/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.expression;

import java.util.Map;

import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

/**
 * <p>The template is used to generate String by template source code. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Template {

	/**
	 * the source code of template
	 */
	private String template;
	
	/**
	 * the compiled template
	 */
	private CompiledTemplate compiledTemplate;
	
	/**
	 * @param template the source code of template
	 */
	public Template(final String template) {
		this.template = template;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.template;
	}
	
	/**
	 * @param template the source code of template
	 * @return the compiled template
	 */
	public static Template getTemplate(final String template) {
		return new Template(template);
	}
	
	/**
	 * @return the compiled template
	 */
	public Template compile() {
		this.getCompiledTemplate();
		return this;
	}
	
	/**
	 * @return the compiled template
	 */
	private CompiledTemplate getCompiledTemplate() {
		
		if (this.compiledTemplate == null) {
			this.compiledTemplate = TemplateCompiler.compileTemplate(this.template);
		}
		return this.compiledTemplate;
	}

	/**
	 * @return the result string context
	 */
	public String execute() {
		return (String) TemplateRuntime.execute(this.getCompiledTemplate());
	}

	/**
	 * @param context the root context
	 * @return the result string context
	 */
	public String execute(final Object context) {
		return (String) TemplateRuntime.execute(this.getCompiledTemplate(), context);
	}

	/**
	 * @param context the root context
	 * @param variableMap the variables map
	 * @return the result string context
	 */
	public String execute(final Object context, final Map<String, ?> variableMap) {
		return (String) TemplateRuntime.execute(this.getCompiledTemplate(), context, variableMap);
	}

	/**
	 * @param variableMap the variables map
	 * @return the result string context
	 */
	public String execute(final Map<String, ?> variableMap) {
		return (String) TemplateRuntime.execute(this.getCompiledTemplate(), variableMap);
	}
}
