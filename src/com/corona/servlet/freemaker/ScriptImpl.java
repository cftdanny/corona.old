/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import com.corona.context.ContextManager;
import com.corona.servlet.ProduceException;

import freemarker.template.Template;

/**
 * <p>The compiled Velocity template. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ScriptImpl implements Script {

	/**
	 * the compiled Velocity template
	 */
	private Template template;
	
	/**
	 * @param template the compiled Velocity template
	 */
	ScriptImpl(final Template template) {
		this.template = template;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.velocity.Script#execute(
	 * 	com.corona.context.ContextManager, java.lang.Object, java.io.OutputStream
	 * )
	 */
	@Override
	public void execute(
			final ContextManager contextManager, final Object root, final OutputStream out) throws ProduceException {
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("this", root);
		
		OutputStreamWriter writer = new OutputStreamWriter(out);
		try {
			this.template.process(context, writer);
			writer.flush();
		} catch (Throwable e) {
			throw new ProduceException("Fail to execute template", e);
		}
	}
}
