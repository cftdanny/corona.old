/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.freemaker;

import java.util.Locale;

import javax.servlet.ServletContext;

import com.corona.context.annotation.Create;
import com.corona.context.annotation.Install;
import com.corona.context.annotation.Inject;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * <p>The implementation class of {@link FreeMakerEngine}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Install(dependencies = "freemarker.template.Configuration")
public class FreeMakerEngineImpl implements FreeMakerEngine {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(FreeMakerEngineImpl.class);
	
	/**
	 * the FreeMaker configuration
	 */
	private Configuration configuration;

	/**
	 * the SERVLET context
	 */
	@Inject private ServletContext servletContext;
	
	/**
	 * the configurator that is used to configure FreeMaker configuration
	 */
	private FreeMakerConfigurator configurator = new DefaultFreeMakerConfigurator();
	
	/**
	 * all theme templates
	 */
	private FreeMakerThemes themes = new DefaultFreeMakerThemes();
	
	/**
	 * the default theme name
	 */
	private String defaultThemeName;
	
	/**
	 * the variable name is used to include child template
	 */
	private String variableNameforChildTemplate = "template";
	
	/**
	 * the applied theme name to get from request or session by attribute name 
	 */
	private String requestAttributeNameForTheme = "theme";

	/**
	 * @return the configurator to configure FreeMaker defined configuration
	 */
	public FreeMakerConfigurator getConfigurator() {
		return configurator;
	}
	
	/**
	 * @param configurator the configurator to configure FreeMaker defined configuration to set
	 */
	public void setConfigurator(final FreeMakerConfigurator configurator) {
		this.configurator = configurator;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.freemaker.FreeMakerEngine#getThemes()
	 */
	public FreeMakerThemes getThemes() {
		return this.themes;
	}

	/**
	 * @param themes all theme templates
	 */
	public void setThemes(final FreeMakerThemes themes) {
		this.themes = themes;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.freemaker.FreeMakerEngine#getDefaultThemeName()
	 */
	public String getDefaultThemeName() {
		return defaultThemeName;
	}
	
	/**
	 * @param defaultThemeName the default theme name to set
	 */
	public void setDefaultThemeName(final String defaultThemeName) {
		this.defaultThemeName = defaultThemeName;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.freemaker.FreeMakerEngine#getVariableNameForChildTemplate()
	 */
	public String getVariableNameForChildTemplate() {
		return variableNameforChildTemplate;
	}

	/**
	 * @param themeTemplateVariableName the variable name is used to include child template to set
	 */
	public void setVariableNameForChildTemplate(final String themeTemplateVariableName) {
		this.variableNameforChildTemplate = themeTemplateVariableName;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.freemaker.FreeMakerEngine#getRequestAttributeNameForTheme()
	 */
	public String getRequestAttributeNameForTheme() {
		return requestAttributeNameForTheme;
	}
	
	/**
	 * @param requestAttributeNameForTheme the applied theme name to get from request by attribute name to set
	 */
	public void setRequestAttributeNameForTheme(final String requestAttributeNameForTheme) {
		this.requestAttributeNameForTheme = requestAttributeNameForTheme;
	}

	/**
	 * initialize FreeMaker template engine
	 */
	@Create public void init() {
		
		// configure logging library of FreeMaker to Java Logging Framework
		try {
			freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_JAVA);
		} catch (ClassNotFoundException e) {
			this.logger.error("Fail to set java logging library to FreeMaker", e);
		}
		this.configuration = new Configuration();
		this.configurator.configure(servletContext, configuration);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.freemaker.FreeMakerEngine#compile(java.lang.String, java.util.Locale)
	 */
	public Template compile(final String name, final Locale locale) throws Exception {
		
		try {
			return this.configuration.getTemplate(name, locale);
		} catch (Exception e) {
			this.logger.error("Fail to comile FreeMaker template [{0}]", e, name);
			throw e;
		}
	}
}
