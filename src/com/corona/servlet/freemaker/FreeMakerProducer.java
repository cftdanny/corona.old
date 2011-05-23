/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.ServletRequest;

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
		
		// find FreeMaker engine is used to compile and process that request
		FreeMakerEngineManager manager = contextManager.get(FreeMakerEngineManager.class, this.engine);
		if (manager == null) {
			this.logger.error("FreeMaker engine [{0}] is not configured, please configure it", this.engine);
			throw new ProduceException("FreeMaker engine [{0}] is not configured, please configure it", this.engine);
		}
		
		// compile FreeMaker template by found FreeMaker engine
		ServletRequest request = contextManager.get(ServletRequest.class);
		Template compiled = null;
		try {
			compiled = manager.compile(this.template, request.getLocale());
		} catch (IOException e) {
			this.logger.error("Fail to compile FreeMaker template [{0}]", e, this.template);
			throw new ProduceException("Fail to compile FreeMaker template [{0}]", e, this.template);
		}
		
		// process compiled template with variable context
		OutputStreamWriter writer = new OutputStreamWriter(out);
		try {
			compiled.process(new FreeMakerDataModel(contextManager, root), writer);
			writer.flush();
		} catch (Exception e) {
			this.logger.error("Fail to process FreeMaker template [{0}]", e, this.template);
			throw new ProduceException("Fail to process FreeMaker template [{0}]", e, this.template);
		}
	}
}
