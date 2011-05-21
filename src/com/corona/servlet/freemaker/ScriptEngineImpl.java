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

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * <p>The implementation class of Velocity </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Dependency("org.apache.velocity.app.VelocityEngine")
public class ScriptEngineImpl implements ScriptEngine {

	/**
	 * <p>How to load script: from FILE or CLASS path </p>
	 */
	public enum ResourceLoader {
		
		/**
		 * from file
		 */
		FILE,
		
		/**
		 * from class path
		 */
		CLASS
	}
	
	/**
	 * the FreeMaker configuration
	 */
	private Configuration config;

	/**
	 * the SERVLET context
	 */
	@Inject private ServletContext servletContext;
	
	/**
	 * the resource loader
	 */
	private ResourceLoader resourceLoader = ResourceLoader.FILE;
	
	/**
	 * the root path
	 */
	private String basePath = "/WEB-INF";
	
	/**
	 * @return where (FILE or CLASS) to load script
	 */
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	/**
	 * @param resourceLoader where (FILE or CLASS) to load script to set
	 */
	public void setResourceLoader(final ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
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
		
		this.config = new Configuration();
		this.config.setObjectWrapper(new DefaultObjectWrapper());
		switch (this.resourceLoader) {
			case FILE:
				this.config.setServletContextForTemplateLoading(this.servletContext, this.basePath);
				break;
				
			default:
				this.config.setClassForTemplateLoading(Class.forName(this.getBasePath()), "");
				break;
		}
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
