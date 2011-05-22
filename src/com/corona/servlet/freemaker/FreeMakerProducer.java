/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import com.corona.context.ContextManager;
import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.AbstractProducer;
import com.corona.servlet.ProduceException;
import com.corona.servlet.annotation.FreeMaker;

import freemarker.template.Template;

/**
 * <p>This producer is used to create HTTP response by FreeMaker and annotated method in component. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class FreeMakerProducer extends AbstractProducer {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(FreeMakerProducer.class);
	
	/**
	 * the FreeMaker engine
	 */
	private String engine;
	
	/**
	 * the FreMaker template name
	 */
	private String template;
	
	/**
	 * @param key the component key
	 * @param method the method that is annotated with {@link FreeMaker}
	 */
	public FreeMakerProducer(final Key<?> key, final DecoratedMethod method) {
		super(key, method);

		FreeMaker freemaker = method.getMethod().getAnnotation(FreeMaker.class);

		this.template = freemaker.value();
		this.engine = freemaker.engine();
		if (this.engine.trim().length() == 0) {
			this.engine = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Producer#produce(
	 * 	com.corona.context.ContextManager, java.lang.Object, java.io.OutputStream
	 * )
	 */
	@Override
	public void produce(
			final ContextManager contextManager, final Object root, final OutputStream out) throws ProduceException {
		
		FreeMakerEngineManager manager = contextManager.get(FreeMakerEngineManager.class, this.engine);
		if (manager == null) {
			
		}
		
		ServletRequest request = contextManager.get(ServletRequest.class);
		Template compiled = null;
		try {
			compiled = manager.compile(this.template, request.getLocale());
		} catch (IOException e) {
			throw new ProduceException("", e);
		}
		
		// prepare context variables in order to process compiled template
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("this", root);
		context.put("request", request);
		context.put("response", contextManager.get(ServletResponse.class));
		context.put("session", contextManager.get(HttpSession.class));
		
		// process compiled template with variable context
		OutputStreamWriter writer = new OutputStreamWriter(out);
		try {
			compiled.process(context, writer);
			writer.flush();
		} catch (Exception e) {
			
		}
	}
}
