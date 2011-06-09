/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import java.util.Locale;

import javax.servlet.ServletContext;

import freemarker.cache.MruCacheStorage;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * <p>The default configurator that is used to configure FreeMaker defined configuration </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DefaultFreeMakerConfigurator implements FreeMakerConfigurator {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.freemaker.FreeMakerConfigurator#configure(
	 * 	javax.servlet.ServletContext, freemarker.template.Configuration
	 * )
	 */
	@Override
	public void configure(final ServletContext servletContext, final Configuration configuration) {

		// Configure FreeMaker template settings
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setTemplateLoader(new WebappTemplateLoader(servletContext, "/WEB-INF/template"));
		configuration.setCacheStorage(new MruCacheStorage(8, 80));
		
		// set default encoding and locale to FreeMaker engine
		configuration.setDefaultEncoding("UTF-8");
		configuration.setLocale(new Locale("en", "US"));
		configuration.setLocalizedLookup(true);

		// set template new version checking and white space strip
		configuration.setTemplateUpdateDelay(3);
		configuration.setWhitespaceStripping(false);
	}
}
