/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.expression;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import com.corona.util.ResourceUtil;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * <p>Template implement by FreeMaker </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Template {

	/**
	 * the compiled FreeMaker template
	 */
	private freemarker.template.Template template;
	
	/**
	 * @param template the FreeMaker template
	 */
	public Template(final String template) {
		
		Configuration configuration = new Configuration();

		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setTemplateLoader(new StringTemplateLoader());

		try {
			this.template = new freemarker.template.Template("", new StringReader(template), configuration);
		} catch (IOException e) {
			this.template = null;
		}
	}

	/**
	 * @param type the class that is used to load resource template
	 * @param resource the resource name
	 */
	public Template(final Class<?> type, final String resource) {
		this(ResourceUtil.load(type, resource));
	}

	/**
	 * @param template the template source code
	 * @return the template
	 */
	public static Template getTemplate(final String template) {
		return new Template(template);
	}
	
	/**
	 * @return the string template loader
	 */
	private StringTemplateLoader getTemplateLoader() {
		return (StringTemplateLoader) this.template.getConfiguration().getTemplateLoader();
	}
	
	/**
	 * @param name the template name
	 * @param source the template source
	 */
	public void install(final String name, final String source) {
		this.getTemplateLoader().putTemplate(name, source);
	}
	
	/**
	 * @param name the template name
	 * @param type the class that is used to load resource template
	 * @param resource the resource name
	 */
	public void install(final String name, final Class<?> type, final String resource) {
		this.install(name, ResourceUtil.load(type, resource));
	}

	/**
	 * @param context the context
	 * @return the result
	 */
	private String execute(final DataModel context) {
		
		StringWriter writer = new StringWriter();
		try {
			this.template.process(context, writer);
		} catch (Exception e) {
			throw new TemplateException(e);
		}
		return writer.toString();
	}
	
	/**
	 * @param context the root context
	 * @return the result string context
	 */
	public String execute(final Object context) {
		return this.execute(new DataModel(context, null));
	}

	/**
	 * @param context the root context
	 * @param variableMap the variables map
	 * @return the result string context
	 */
	public String execute(final Object context, final Map<String, ?> variableMap) {
		return this.execute(new DataModel(context, variableMap));
	}

	/**
	 * @param variableMap the variables map
	 * @return the result string context
	 */
	public String execute(final Map<String, ?> variableMap) {
		return this.execute(new DataModel(null, variableMap));
	}
}
