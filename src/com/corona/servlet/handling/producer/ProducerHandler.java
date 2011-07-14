/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.handling.producer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.InjectMethod;
import com.corona.context.Key;
import com.corona.context.annotation.Transactional;
import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
import com.corona.data.Transaction;
import com.corona.data.TransactionManager;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.AbstractHandler;
import com.corona.servlet.HandleException;
import com.corona.servlet.MatchResult;
import com.corona.servlet.Matcher;
import com.corona.servlet.Producer;
import com.corona.servlet.ProducerHint;
import com.corona.servlet.annotation.ContentType;
import com.corona.servlet.annotation.Expiration;
import com.corona.servlet.annotation.Track;
import com.corona.servlet.tracking.Finger;
import com.corona.servlet.tracking.TrackManager;
import com.corona.util.ServletUtil;

/**
 * <p>This handler is used to create HTTP response by an injection method in component. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ProducerHandler extends AbstractHandler {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(ProducerHandler.class);
	
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
	 * the track annotation in this method
	 */
	private Track track;
	
	/**
	 * @param matcher the matcher
	 * @param producer the producer to create HTTP response
	 */
	public ProducerHandler(final Matcher matcher, final Producer producer) {
		super(matcher);
		
		this.producer = producer;
		
		Method method = this.producer.getInjectMethod().getMethod();
		this.expiration = method.getAnnotation(Expiration.class);
		this.contentType = method.getAnnotation(ContentType.class);
		this.track = method.getAnnotation(Track.class);
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
		
		// store match result to current context, it can be used for injection
		context.put(new Key<MatchResult>(MatchResult.class), result);
		
		// create ProduceHint in order to set producer runtime information later
		ProducerHint hint = new ProducerHint();
		context.put(new Key<ProducerHint>(ProducerHint.class), hint);

		// if need track this request, will store tracked information for track manager later
		Finger finger = null;
		if (this.track != null) {
			finger = new Finger();
			context.put(new Key<Finger>(Finger.class), finger);
		}
		
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
		
		// get the method to be executed in order to produce content
		InjectMethod injectMethod = this.producer.getInjectMethod();
		Method method = injectMethod.getMethod();
		
		// invoke annotated method and produce web content by template and method result 
		Object outcome = null;
		if (method.isAnnotationPresent(Transactional.class)) {
			outcome = this.execute(contextManager, component, injectMethod);
		} else {
			outcome = injectMethod.invoke(contextManager, component);
		}
		hint.setOutcome(outcome);
		
		// create response output by producer's outcome
		try {
			this.producer.produce(contextManager, response, response.getOutputStream(), component, outcome);
		} catch (Throwable e) {
			
			// if need track this request, will store this exception
			if (finger != null) {
				finger.setError(e);
			}
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
		
		// if track this request, will store the tracked information to track manager
		if (finger != null) {
			
			TrackManager trackManager = contextManager.get(TrackManager.class);
			if (trackManager != null) {
				
				// store other information for tracked information
				finger.setCode(this.track.code());
				finger.setPath(request.getPathInfo());
				for (String parameterName : this.track.value()) {
					String parameterValue = request.getParameter(parameterName);
					if (parameterValue != null) {
						finger.getParameters().put(parameterName, parameterValue);
					}
				}
				finger.setAfter(new Date());
				
				trackManager.track(finger);
			}
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
			final ContextManager contextManager, final Object component, final InjectMethod method
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
			
			this.logger.error("Fail to invoke method [{0}] within transaction, will roll back", e, method.getMethod());
			throw new HandleException(
					"Fail to invoke method [{0}] within transaction, will roll back", e, method.getMethod()
			);
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
