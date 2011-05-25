/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.velocity;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.AbstractProducer;
import com.corona.servlet.ProduceException;
import com.corona.servlet.annotation.Velocity;

/**
 * <p>This producer is used to create HTTP response by annotated method in component. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class VelocityProducer extends AbstractProducer {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(VelocityProducer.class);
	
	/**
	 * the compiled Velocity script
	 */
	private Script script;
	
	/**
	 * the Velocity script engine
	 */
	private String engine;
	
	/**
	 * the script name
	 */
	private String name;
	
	/**
	 * the character encoding of script file
	 */
	private String encoding;
	
	/**
	 * whether cache compiled Velocity script
	 */
	private boolean cached;
	
	/**
	 * @param key the component key
	 * @param method the annotated producer method
	 */
	public VelocityProducer(final Key<?> key, final DecoratedMethod method) {
		super(key, method);
		
		Velocity velocity = method.getMethod().getAnnotation(Velocity.class);
		this.engine = velocity.engine();
		this.name = velocity.value();
		this.cached = velocity.cache();
		this.encoding = velocity.encoding();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Producer#produce(
	 * 	com.corona.context.ContextManager, javax.servlet.http.HttpServletResponse, java.io.OutputStream, 
	 * 	java.lang.Object
	 * )
	 */
	@Override
	public void produce(
			final ContextManager contextManager, final HttpServletResponse response, final OutputStream out, 
			final Object data
	) throws ProduceException {
		
		Script running = this.cached ? this.script : null;
		if (running == null) {
			
			ScriptEngine scriptEngine = contextManager.get(
					ScriptEngine.class, "".equals(this.engine) ? null : this.encoding
			);
			if (scriptEngine == null) {
				if ("".equals(this.engine)) {
					this.logger.error("Velocity engine is not configured");
					throw new ConfigurationException("Velocity engine is not configured");
				} else {
					this.logger.error("Velocity engine [{0}] is not configured", engine);
					throw new ConfigurationException("Velocity engine [{0}] is not configured", this.engine);
				}
			}
			
			running = scriptEngine.compile(this.name, this.encoding);
			if (this.cached) {
				this.script = running;
			}
		}
		
		// set content type if it is not set yet
		if (response.getContentType() == null) {
			response.setContentType("text/html");
		}

		// create HTML response by root object and Velocity template
		running.execute(contextManager, data, out);
	}
}
