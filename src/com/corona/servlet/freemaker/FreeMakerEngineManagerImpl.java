/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletContext;

import com.corona.context.annotation.Create;
import com.corona.context.annotation.Dependency;
import com.corona.context.annotation.Inject;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * <p>The implementation class of FreeMaker </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Dependency("freemarker.template.Configuration")
public class FreeMakerEngineManagerImpl implements FreeMakerEngineManager {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog("FreeMaker");
	
	/**
	 * the FreeMaker configuration
	 */
	private Configuration configuration;

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
		
		// configure logging library of FreeMaker to Java Logging Framework
		try {
			freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_JAVA);
		} catch (ClassNotFoundException e) {
			this.logger.error("Fail to set java logging library to FreeMaker", e);
		}
		this.configuration = new Configuration();
		
		// will load FreeMaker template from ServletContext path
		this.configuration.setObjectWrapper(new DefaultObjectWrapper());
		this.configuration.setServletContextForTemplateLoading(
				this.servletContext, this.basePath
		);
		
		// set default encoding and locale to FreeMaker engine
		this.configuration.setDefaultEncoding("UTF-8");
		this.configuration.setLocale(new Locale("en", "US"));
	}

	/**
	 * @return the FreeMaker configuration
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * @return the default encoding for FreeMaker template 
	 */
	public String getDefaultEncoding() {
		return this.configuration.getDefaultEncoding();
	}
	
	/**
	 * @param encoding the default encoding for FreeMaker template
	 */
	public void setDefaultEncoding(final String encoding) {
		this.configuration.setDefaultEncoding(encoding);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.freemaker.FreeMakerEngineManager#compile(java.lang.String, java.util.Locale)
	 */
	public Template compile(final String name, final Locale locale) throws IOException {
		return this.configuration.getTemplate(name, locale);
	}
}
