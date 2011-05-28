/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;
import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
import com.corona.data.Transaction;
import com.corona.data.TransactionManager;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.ContentType;
import com.corona.servlet.annotation.Expiration;
import com.corona.servlet.annotation.Transactional;

/**
 * <p>This handler is used to create HTTP response by an injection method in component. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ComponentHandler extends AbstractHandler {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(ComponentHandler.class);
	
	/**
	 * the producer to create HTTP response
	 */
	private Producer producer;
	
	/**
	 * how to control HTTP response expires
	 */
	private Expiration expiration;
	
	/**
	 * the HTTP content type that is annotated in method of component
	 */
	private ContentType contentType;
	
	/**
	 * @param matcher the matcher
	 * @param producer the producer to create HTTP response
	 */
	ComponentHandler(final Matcher matcher, final Producer producer) {
		super(matcher);
		
		this.producer = producer;
		this.expiration = this.getProducerMethod().getAnnotation(Expiration.class);
		this.contentType = this.getProducerMethod().getAnnotation(ContentType.class);
	}

	/**
	 * @return the producer method in component
	 */
	private Method getProducerMethod() {
		return this.producer.getDecoratedMethod().getMethod();
	}
	
	/**
	 * @param request the HTTP SERVLET request
	 * @return the context manager factory that store in SERVLET context before
	 */
	private ContextManagerFactory getContextManagerFactory(final HttpServletRequest request) {
		return ServletUtil.getContextManagerFactory(request.getSession().getServletContext());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Handler#handle(
	 * 	com.corona.servlet.MatchResult, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse
	 * )
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void handle(
			final MatchResult result, final HttpServletRequest request, final HttpServletResponse response
	) throws HandleException {
		
		Map<Key, Object> context = new HashMap<Key, Object>();
		
		// store built-in components and instances to context manager 
		context.put(new Key<ServletRequest>(ServletRequest.class), (ServletRequest) request);
		context.put(new Key<HttpServletRequest>(HttpServletRequest.class), request);
		context.put(new Key<ServletResponse>(ServletResponse.class), (ServletResponse) response);
		context.put(new Key<HttpServletResponse>(HttpServletResponse.class), response);
		context.put(new Key<HttpSession>(HttpSession.class), request.getSession());
		
		// store all matched parameter and values to request
		for (String name : result) {
			request.setAttribute(name, result.get(name));
		}
		
		// create ProduceHint in order to set producer runtime information later
		ProduceHint hint = new ProduceHint();
		context.put(new Key<ProduceHint>(ProduceHint.class), hint);

		// set HTTP expires by Expires annotation in producer method of component
		if ((this.expiration == null) || (this.expiration.value() < 0)) {
			
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setDateHeader("Last-Modified", System.currentTimeMillis());
		} else {
			
			Long current = System.currentTimeMillis();
			response.setHeader("Cache-Control", "max-age=" + (this.expiration.value() / 1000));
			response.setDateHeader("Expires", current + this.expiration.value());
			response.setDateHeader("Last-Modified", current);
		}

		// set HTTP response content type if configured by annotation in producer method
		if (this.contentType != null) {
			response.setContentType(this.contentType.value());
		}

		// create context manager and resolve component from context manager factory
		ContextManager contextManager = this.getContextManagerFactory(request).create(context);
		Object component = contextManager.get(this.producer.getKey());
		hint.setComponent(component);
		
		// invoke annotated method and produce web content by template and method result 
		Object outcome = null;
		if (this.producer.getDecoratedMethod().getMethod().isAnnotationPresent(Transactional.class)) {
			outcome = this.execute(contextManager, component, this.producer.getDecoratedMethod());
		} else {
			outcome = this.producer.getDecoratedMethod().invoke(contextManager, component);
		}
		hint.setOutcome(outcome);
		
		// create response output by producer's outcome
		try {
			this.producer.produce(contextManager, response, response.getOutputStream(), outcome);
		} catch (Throwable e) {
			
			Method method = this.producer.getDecoratedMethod().getMethod();
			this.logger.error("Fail to produce web content by method [{0}]", e, method); 
			throw new HandleException("Fail to produce web content by method [{0}]", e, method);
		}
		
		// flush produced content to HTTP response stream
		try {
			response.getOutputStream().flush();
		} catch (IOException e) {
			this.logger.error("Fail to flush produced content to HTTP response", e);
			throw new HandleException("Fail to flush produced content to HTTP response", e);
		}
		
		// close context manager in order to release resources that is allocated by it
		try {
			contextManager.close();
		} catch (Exception e) {
			this.logger.error("Fail to close context manager", e);
			throw new HandleException("Fail to close context manager", e);
		}
	}
	
	/**
	 * @param contextManager current context manager
	 * @param component the component
	 * @param method the producer method
	 * @return the outcome return by producer method
	 * @throws HandleException if fail to invoke method in transaction
	 */
	private Object execute(
			final ContextManager contextManager, final Object component, final DecoratedMethod method
	) throws HandleException {

		// open default connection manager from context container
		ConnectionManager connectionManager = null;
		try {
			connectionManager = this.getConnectionManager(contextManager);
		} catch (Exception e) {
			throw new HandleException("Fail to get default connection manager, maybe is not registered", e);
		}

		// try to get container managed transaction first, if can'n, get connection manager's transaction
		Transaction transaction = this.getTransaction(contextManager);
		if (transaction == null) {
			transaction = connectionManager.getTransaction();
		}

		// try to start transaction, if can't, throw exception and close connection manager
		try {
			transaction.begin();
		} catch (Exception e) {
			
			// fail start transaction, will close connection manager
			try {
				connectionManager.close();
			} catch (Exception e1) {
				throw new HandleException("Fail to close default connection manager", e);
			}
			throw new HandleException("Fail to start transaction before invoke producer method", e);
		}
		
		// execute producer business logic method in transaction
		try {
			Object outcome = method.invoke(contextManager, component);
			if (!transaction.getRollbackOnly()) {
				transaction.commit();
			} else {
				transaction.rollback();
			}
			return outcome;
			
		} catch (Throwable e) {
			
			try {
				transaction.rollback();
			} catch (Exception ex) {
				this.logger.warn("Fail to roll back , but do not know how to handle this case, just ignore", e);
			}
			
			this.logger.error("Fail to invoke method [{0}] within transaction, will roll back", e, method);
			throw new HandleException("Fail to invoke method [{0}] within transaction, will roll back", e, method);
			
		} finally {
			
			// try to close connection manager, if error, just skip it
			try {
				connectionManager.close();
			} catch (Exception e) {
				this.logger.warn(
						"Fail to close connection manager, but changed data has been commit or roll back", e
				);
			}
		}
	}
	
	/**
	 * @param contextManager the current context manager
	 * @return the default connection manager
	 * @throws Exception if fail to open connection manager
	 */
	private ConnectionManager getConnectionManager(final ContextManager contextManager) throws Exception {
		return contextManager.get(ConnectionManagerFactory.class).open();
	}
	
	/**
	 * @param contextManager the current context manager
	 * @return the container managed transaction
	 */
	private Transaction getTransaction(final ContextManager contextManager) {

		TransactionManager transactionManager = contextManager.get(TransactionManager.class);
		return (transactionManager != null) ? transactionManager.getTransaction() : null;
	}
}
