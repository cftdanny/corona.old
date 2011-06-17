/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.json;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;
import com.corona.context.InjectMethod;
import com.corona.context.Key;
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
	public JsonProducer(final Key<?> key, final InjectMethod method) {
		super(key, method);
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
		
		// get JSON content generator from context manager
		JsonMarshaller generator = contextManager.get(JsonMarshaller.class);
		if (generator == null) {
			this.logger.error("JSON Marshaller does not exist, please install and configure it in module");
			throw new ProduceException(
					"JSON Marshaller does not exist, please install and configure it in module"
			);
		}
		
		// set content type if it is not set yet
		if (response.getContentType() == null) {
			response.setContentType("application/json");
		}
		
		// marshal root object from producer method to JSON
		generator.marshal(out, data);
	}
}
