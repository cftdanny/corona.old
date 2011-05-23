/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
 * <p>The implementation class of {@link FreeMakerEngineManager}. </p>
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
	 * the default encoding for template
	 */
	private String defaultEncoding = "UTF-8";
	
	/**
	 * the default locale for template
	 */
	private Locale defaultLocale = new Locale("en", "US");
	
	/**
	 * all auto-imported templates
	 */
	private Map<String, String> importedTemplates = new HashMap<String, String>();
	
	/**
	 * all auto-included templates
	 */
	private List<String> includedTemplates = new ArrayList<String>();
	
	/**
	 * the time in seconds that must elapse before checking whether there is a newer version
	 */
	private int templateUpdateDelay = 3;
	
	/**
	 * enable or disable localized template lookup
	 */
	private boolean localizedLookup = true;
	
	/**
	 * whether strip white space around certain FTL tags
	 */
	private boolean whitespaceStripping = false;
	
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
		if (this.configuration != null) {
			this.configuration.setServletContextForTemplateLoading(
					this.servletContext, this.basePath
			);
		}
	}

	/**
	 * @return the default encoding for FreeMaker template 
	 */
	public String getDefaultEncoding() {
		return this.defaultEncoding;
	}
	
	/**
	 * @param defaultEncoding the default encoding for FreeMaker template
	 */
	public void setDefaultEncoding(final String defaultEncoding) {
		
		this.defaultEncoding = defaultEncoding;
		if (this.configuration != null) {
			this.configuration.setDefaultEncoding(this.defaultEncoding);
		}
	}
	
	/**
	 * @return the default locale for template
	 */
	public Locale getDefaultLocale() {
		return defaultLocale;
	}
	
	/**
	 * @param defaultLocale the default locale for template
	 */
	public void setDefaultLocale(final Locale defaultLocale) {
		
		this.defaultLocale = defaultLocale;
		if (this.configuration != null) {
			this.configuration.setLocale(this.defaultLocale);
		}
	}

	/**
	 * @return all auto-included templates
	 */
	public List<String> getIncludedTemplates() {
		return includedTemplates;
	}
	
	/**
	 * @param includedTemplates all auto-included templates
	 */
	public void setIncludedTemplates(final List<String> includedTemplates) {
		
		this.includedTemplates = includedTemplates;
		if (this.configuration != null) {
			this.configuration.setAutoIncludes(this.includedTemplates);
		}
	}
	
	/**
	 * @return all auto-imported templates
	 */
	public Map<String, String> getImportedTemplates() {
		return importedTemplates;
	}

	/**
	 * @param importedTemplates all auto-imported templates
	 */
	public void setImportedTemplates(final Map<String, String> importedTemplates) {
		
		this.importedTemplates = importedTemplates;
		if (this.configuration != null) {
			this.configuration.setAutoImports(this.importedTemplates);
		}
	}
	
	/**
	 * @return the time in seconds that must elapse before checking whether there is a newer version
	 */
	public int getTemplateUpdateDelay() {
		return templateUpdateDelay;
	}

	/**
	 * @param templateUpdateDelay the time in seconds that must elapse before checking newer version
	 */
	public void setTemplateUpdateDelay(final int templateUpdateDelay) {
		
		this.templateUpdateDelay = templateUpdateDelay;
		if (this.configuration != null) {
			this.configuration.setTemplateUpdateDelay(this.templateUpdateDelay);
		}
	}

	/**
	 * @return enable or disable localized template lookup
	 */
	public boolean isLocalizedLookup() {
		return localizedLookup;
	}
	
	/**
	 * @param localizedLookup enable or disable localized template lookup
	 */
	public void setLocalizedLookup(final boolean localizedLookup) {
		
		this.localizedLookup = localizedLookup;
		if (this.configuration != null) {
			this.configuration.setLocalizedLookup(this.localizedLookup);
		}
	}
	
	/**
	 * @return whether strip white space around certain FTL tags
	 */
	public boolean isWhitespaceStripping() {
		return whitespaceStripping;
	}
	
	/**
	 * @param whitespaceStripping whether strip white space around certain FTL tags
	 */
	public void setWhitespaceStripping(final boolean whitespaceStripping) {
		
		this.whitespaceStripping = whitespaceStripping;
		if (this.configuration != null) {
			this.setWhitespaceStripping(this.whitespaceStripping);
		}
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
		
		// will load FreeMaker template from ServletContext path
		this.configuration.setObjectWrapper(new DefaultObjectWrapper());
		this.configuration.setServletContextForTemplateLoading(
				this.servletContext, this.basePath
		);
		
		// set default encoding and locale to FreeMaker engine
		this.configuration.setDefaultEncoding(this.defaultEncoding);
		this.configuration.setLocale(this.defaultLocale);
		this.configuration.setLocalizedLookup(this.localizedLookup);
		
		// add all auto-import and auto-include templates to FreeMaker configuration
		this.configuration.setAutoIncludes(this.includedTemplates);
		this.configuration.setAutoImports(this.importedTemplates);
		
		// set template new version checking and white space strip
		this.configuration.setTemplateUpdateDelay(this.templateUpdateDelay);
		this.configuration.setWhitespaceStripping(this.whitespaceStripping);
	}

	/**
	 * @return the FreeMaker configuration
	 */
	public Configuration getConfiguration() {
		return this.configuration;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.freemaker.FreeMakerEngineManager#compile(java.lang.String, java.util.Locale)
	 */
	public Template compile(final String name, final Locale locale) throws IOException {
		return this.configuration.getTemplate(name, locale);
	}
}
