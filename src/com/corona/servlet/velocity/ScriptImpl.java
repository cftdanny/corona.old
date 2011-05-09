/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.velocity;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.corona.context.ContextManager;
import com.corona.servlet.ProduceException;

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
		
		VelocityContext context = new VelocityContext();
		context.put("this", root);
		
		OutputStreamWriter writer = new OutputStreamWriter(out);
		try {
			this.template.merge(context, writer);
			writer.flush();
		} catch (Throwable e) {
			throw new ProduceException("Fail to execute script", e);
		}
	}
}
