/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

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
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.ContentType;
import com.corona.servlet.annotation.Expiration;

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
		
		// invoke annotated method and produce web content by template and method result 
		Object outcome = this.producer.getDecoratedMethod().invoke(contextManager, component);
		try {
			this.producer.produce(contextManager, response, response.getOutputStream(), outcome);
		} catch (Throwable e) {
			
			Method method = this.producer.getDecoratedMethod().getMethod();
			this.logger.error("Fail to produce web content by method [{0}]", e, method); 
			throw new HandleException("Fail to produce web content by method [{0}]", e, method);
		}
	}
}
