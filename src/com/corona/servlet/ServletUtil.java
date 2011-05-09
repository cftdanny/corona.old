/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import javax.servlet.ServletContext;

import com.corona.context.ContextManagerFactory;

/**
 * <p>Utility class for SERVLET </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ServletUtil {

	/**
	 * utility class
	 */
	protected ServletUtil() {
		// do nothing
	}
	
	/**
	 * <p>Get context manager factory from SERVLET context that is stored by {@link ApplicationLoader}. </p>
	 * 
	 * @param servletContext the SERVLET context
	 * @return the context manager factory
	 */
	public static ContextManagerFactory getContextManagerFactory(final ServletContext servletContext) {
		return (ContextManagerFactory) servletContext.getAttribute(ApplicationLoader.CONTEXT);
	}
	
	/**
	 * <p>Get handlers from SERVLET context that is stored by {@link ApplicationLoader}. </p>
	 * 
	 * @param servletContext the SERVLET context
	 * @return the handlers
	 */
	public static Handlers getHandlers(final ServletContext servletContext) {
		return (Handlers) servletContext.getAttribute(ApplicationLoader.HANDLERS);
	}
}
