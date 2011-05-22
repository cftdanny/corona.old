/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import javax.servlet.ServletContext;

import com.corona.context.annotation.Create;
import com.corona.context.annotation.Dependency;
import com.corona.context.annotation.Inject;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * <p>The implementation class of FreeMaker </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Dependency("freemarker.template.Configuration")
public class ScriptEngineImpl implements ScriptEngine {

	/**
	 * the FreeMaker configuration
	 */
	private Configuration config;

	/**
	 * the SERVLET context
	 */
	@Inject private ServletContext servletContext;
	
	/**
	 * the root path
	 */
	private String basePath = "/WEB-INF";
	
	/**
	 * @return the path
	 */
	public String getBasePath() {
		return basePath;
	}
	
	/**
	 * @param basePath the script base path to set
	 */
	public void setBasePath(final String basePath) {
		this.basePath = basePath;
	}

	/**
	 * initialize Velocity script engine
	 */
	@Create public void init() {
		
		freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_JAVA);
		this.config = new Configuration();
		this.config.setObjectWrapper(new DefaultObjectWrapper());
		
		this.config.setServletContextForTemplateLoading(this.servletContext, this.basePath);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.velocity.ScriptEngine#compile(java.lang.String, java.lang.String)
	 */
	@Override
	public Script compile(final String name) {
		return new ScriptImpl(this.config.getTemplate(name));
	}
}
