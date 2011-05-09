/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.json;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;
import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.AbstractProducer;
import com.corona.servlet.ProduceException;

/**
 * <p>This producer is used to marshal outcome from method to json content. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class JsonProducer extends AbstractProducer {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(JsonProducer.class);
	
	/**
	 * @param key the component key
	 * @param method the annotated producer method
	 */
	public JsonProducer(final Key<?> key, final DecoratedMethod method) {
		super(key, method);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Producer#produce(
	 * 	com.corona.context.ContextManager, java.lang.Object, java.io.OutputStream
	 * )
	 */
	@Override
	public void produce(
			final ContextManager contextManager, final Object root, final OutputStream out
	) throws ProduceException {

		// get JSON content generator from context manager
		Marshaller generator = contextManager.get(Marshaller.class);
		if (generator == null) {
			this.logger.error("JSON generator is not installed, please check module configuration");
			throw new ProduceException("JSON generator is not installed, please check module configuration");
		}
		
		// set content type if it is not set yet
		HttpServletResponse response = contextManager.get(HttpServletResponse.class);
		if (response.getContentType() == null) {
			response.setContentType("application/json");
		}
		
		// marshal root object from producer method to JSON
		generator.marshal(out, root);
	}
}
