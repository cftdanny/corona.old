/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.expression;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.mvel2.templates.TemplateRuntime;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * <p>Template implement by FreeMaker </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Template1 {

	/**
	 * the compiled FreeMaker template
	 */
	private freemarker.template.Template template;
	
	/**
	 * @param template the FreeMaker template
	 */
	public Template1(final String template) {
		
		Configuration configuration = new Configuration();
		try {
			this.template = new freemarker.template.Template("", new StringReader(template), configuration);
		} catch (IOException e) {
			this.template = null;
		}

		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setTemplateLoader(new StringTemplateLoader());
	}
	
	private StringTemplateLoader getTemplateLoader() {
		return (StringTemplateLoader) this.template.getConfiguration().getTemplateLoader();
	}
	
	public void install(final String name, final String source) {
		this.getTemplateLoader().putTemplate(name, source);
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
