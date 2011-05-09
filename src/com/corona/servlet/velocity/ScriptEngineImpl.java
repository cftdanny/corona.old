/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.velocity;

import javax.servlet.ServletContext;

import com.corona.context.annotation.Create;
import com.corona.context.annotation.Dependency;
import com.corona.context.annotation.Inject;

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
	 * the Velocity engine
	 */
	private VelocityEngine engine;

	/**
	 * the SERVLET context
	 */
	@Inject private ServletContext servletContext;
	
	/**
	 * the resource loader
	 */
	private ResourceLoader resourceLoader = ResourceLoader.FILE;
	
	/**
	 * the interval (MS) to check whether source file is changed 
	 */
	private int checkInterval = 1;
	
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
	 * @return the source code modification check interval in MS
	 */
	public int getCheckInterval() {
		return checkInterval;
	}

	/**
	 * @param checkInterval the source code modification check interval in MS to set
	 */
	public void setCheckInterval(final int checkInterval) {
		this.checkInterval = checkInterval;
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
		
		this.engine = new VelocityEngine();
		switch (this.resourceLoader) {
			case FILE:
				this.engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
				this.engine.setProperty(
						RuntimeConstants.FILE_RESOURCE_LOADER_PATH, this.servletContext.getRealPath(this.basePath)
				);
				this.engine.setProperty(
						"file.resource.loader.modificationCheckInterval", Integer.toString(this.checkInterval)
				);
				this.engine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "false");
				break;
				
			default:
				this.engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
				this.engine.setProperty(
						"class.resource.loader.class", ClasspathResourceLoader.class.getName()
				);
				this.engine.setProperty("class.resource.loader.path", "");
				this.engine.setProperty("class.resource.loader.modificationCheckInterval", "0");
				this.engine.setProperty("class.resource.loader.cache", "false");
				break;
		}
		this.engine.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, Log.class.getName());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.velocity.ScriptEngine#compile(java.lang.String, java.lang.String)
	 */
	@Override
	public Script compile(final String name, final String encoding) {
		return new ScriptImpl(this.engine.getTemplate(name, encoding));
	}
}
