/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.redirect;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;
import com.corona.context.InjectMethod;
import com.corona.context.Key;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.AbstractProducer;
import com.corona.servlet.ProduceException;
import com.corona.servlet.annotation.Redirect;

/**
 * <p>This producer is used to create HTTP response by FreeMaker and annotated method in component. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class RedirectProducer extends AbstractProducer {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(RedirectProducer.class);
	
	/**
	 * the URI will redirect to
	 */
	private String uri;
	
	/**
	 * @param key the component key
	 * @param method the method that is annotated with {@link FreeMaker}
	 */
	public RedirectProducer(final Key<?> key, final InjectMethod method) {
		super(key, method);
		this.uri = method.getMethod().getAnnotation(Redirect.class).value();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Producer#produce(
	 * 	com.corona.context.ContextManager, javax.servlet.http.HttpServletResponse, java.io.OutputStream, 
	 * 	java.lang.Object, java.lang.Object
	 * )
	 */
	@Override
	public void produce(
			final ContextManager contextManager, final HttpServletResponse response, final OutputStream out,
			final Object component, final Object data) throws ProduceException {
		
		String redirect = (data == null) ? this.uri : data.toString();
		try {
			response.sendRedirect(redirect);
		} catch (IOException e) {
			this.logger.error("Fail redirect request page to [{0}]", e, redirect);
			throw new ProduceException("Fail redirect request page to [{0}]", e, redirect);
		}
	}
}
