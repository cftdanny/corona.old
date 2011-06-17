/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.freemaker;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;
import com.corona.context.InjectMethod;
import com.corona.context.Key;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.AbstractProducer;
import com.corona.servlet.ProduceException;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.util.StringUtil;

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
	 * whether enable theme for this content
	 */
	private boolean theme;
	
	/**
	 * @param key the component key
	 * @param method the method that is annotated with {@link FreeMaker}
	 */
	public FreeMakerProducer(final Key<?> key, final InjectMethod method) {
		super(key, method);

		FreeMaker freemaker = method.getMethod().getAnnotation(FreeMaker.class);

		this.template = freemaker.value();
		this.engine = freemaker.engine();
		if (this.engine.trim().length() == 0) {
			this.engine = null;
		}
		this.theme = freemaker.theme();
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
		
		// find FreeMaker engine is used to compile and process that request
		FreeMakerEngine manager = contextManager.get(FreeMakerEngine.class, this.engine);
		if (manager == null) {
			this.logger.error("FreeMaker engine [{0}] is not configured, please configure it", this.engine);
			throw new ProduceException("FreeMaker engine [{0}] is not configured, please configure it", this.engine);
		}
		
		// create FreeMaker data model, if data return by producer is NULL, will use Object
		FreeMakerContext dataModel = null;
		if (data != null) {
			dataModel = new FreeMakerContext(contextManager, data);
		} else {
			dataModel = new FreeMakerContext(contextManager, new Object());
		}
		
		// find template to be processed
		String toBeCompiledTemplate = null;
		if (this.theme) {
			
			// pass FreeMaker engine and child template
			dataModel.setFreeMakerEngineManager(manager);
			dataModel.setChildTemplate(this.template);
			
			// find theme template. If not exist, use child template
			toBeCompiledTemplate = dataModel.getParentThemeTemplate();
			if (StringUtil.isBlank(toBeCompiledTemplate)) {
				toBeCompiledTemplate = this.template;
			}
		} else {
			toBeCompiledTemplate = this.template;
		}
		
		// compile FreeMaker template by found FreeMaker engine
		Template compiledTemplate = null;
		try {
			compiledTemplate = manager.compile(toBeCompiledTemplate, dataModel.getRequest().getLocale());
		} catch (Exception e) {
			this.logger.error("Fail to compile FreeMaker template [{0}]", e, this.template);
			throw new ProduceException("Fail to compile FreeMaker template [{0}]", e, this.template);
		}
		
		// set content type if it is not set yet
		if (response.getContentType() == null) {
			response.setContentType("text/html");
		}
		
		// process compiled template with variable context
		OutputStreamWriter writer = new OutputStreamWriter(out);
		try {
			
			compiledTemplate.process(dataModel, writer);
			writer.flush();
		} catch (Exception e) {
			
			this.logger.error("Fail to process FreeMaker template [{0}]", e, this.template);
			throw new ProduceException("Fail to process FreeMaker template [{0}]", e, this.template);
		} finally {
			
			try {
				writer.close();
			} catch (IOException e) {
				this.logger.error("Fail to close writer for FreeMaker template [{0}]", e, this.template);
				throw new ProduceException("Fail to close writer for FreeMaker template [{0}]", e, this.template);
			}
		}
	}
}
