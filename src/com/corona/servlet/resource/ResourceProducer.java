/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.resource;

import java.io.IOException;
import java.io.InputStream;
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
 * <p>This producer is used to create HTTP response by outcome from method annotated with {@link Resource}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ResourceProducer extends AbstractProducer {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(ResourceProducer.class);
	
	/**
	 * @param key the component key
	 * @param method the annotated producer method
	 */
	public ResourceProducer(final Key<?> key, final InjectMethod method) {
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
		
		if (data == null) {
			
			// producer method return a blank (null) resource, send resource name is blank
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource name is blank or empty");
			} catch (IOException e) {
				this.logger.error("Fail to \"Page Not Found\" to response, just skip this error", e);
			}
		} else {
		
			// open resource and write resource and write it to response
			InputStream in = this.getClass().getResourceAsStream(data.toString());
			if (in != null) {
				
				try {
					for (int c = in.read(); c != -1; c = in.read()) {
						out.write(c);
					}
				} catch (IOException e) {
					
					this.logger.error("Fail to write resource [{0}] to response", e, data.toString());
					throw new ProduceException("Fail to write resource [{0}] to response", e, data.toString());
				} finally {
					
					try {
						in.close();
					} catch (IOException e) {
						this.logger.error(
								"Fail to close resource [{0}] stream, just skip this error", e, data.toString()
						);
					}
				}
			} else {
				
				// resource can not be found, send page not find message to client
				this.logger.error("Resource [{0}] does not exist in class path", data.toString());
				try {
					response.sendError(
							HttpServletResponse.SC_NOT_FOUND, "Resource " + data.toString() + " can not be found"
					);
				} catch (IOException e) {
					this.logger.error("Fail to \"Page Not Found\" to response, just skip this error", e);
				}
			}
		}
	}
}
