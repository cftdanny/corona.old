/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.freemaker;

import javax.servlet.ServletContext;

import freemarker.template.Configuration;

/**
 * <p>This class will create a configured FreeMaker configuration in order to compile template in
 * FreeMaker engine. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface FreeMakerConfigurator {

	/**
	 * @param servletContext the SERVLET context
	 * @param configuration the FreeMaker defined configuration
	 */
	void configure(ServletContext servletContext, Configuration configuration);
}