/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.corona.context.AbstractModule;
import com.corona.context.ContextManagerFactory;
import com.corona.context.Initializer;
import com.corona.context.Module;

/**
 * <p>This context listener is used to load context manager factory. Before use it, it can be configured
 * in web.xml by: </p>
 * <pre>
 * 	&lt;listener&gt;
 * 		&lt;listener-class&gt;com.corona.servlet.ApplicationLoader&lt;/listener-class&gt;
 * 	&lt;/listener&gt;
 * </pre>
 *
 * @author $Author$
 * @version $Id$
 */
public class ApplicationLoader implements ServletContextListener {

	/**
	 * the key that store context manager factory in context
	 */
	public static final String CONTEXT = "com.corona.servlet.context";

	/**
	 * the key that store all HTTP response handlers
	 */
	public static final String HANDLERS = "com.corona.servlet.handlers";
	
	/**
	 * @param event the SERVLET context event
	 * @return all modules loaded by service loader
	 */
	private ContextManagerFactory getContextManagerFactory(final ServletContextEvent event) {
		
		List<Module> modules = new ArrayList<Module>();

		// register SERVLET context to container as application scope 
		modules.add(new AbstractModule() {
			protected void configure() {
				this.bindConstant(ServletContext.class).to(event.getServletContext());
			}
		});
		
		// install all web kernel modules that are defined by framework
		for (Module module : ServiceLoader.load(WebKernelModule.class)) {
			modules.add(module);
		}
		// install all web start modules that are defined by application
		for (Module module : ServiceLoader.load(WebStartModule.class)) {
			modules.add(module);
		}
		
		return Initializer.build(modules);
	}
	
	/**
	 * {@inheritDoc}
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(final ServletContextEvent event) {
		
		ContextManagerFactory contextManagerFactory = this.getContextManagerFactory(event);
		
		event.getServletContext().setAttribute(CONTEXT, contextManagerFactory);
		event.getServletContext().setAttribute(
				HANDLERS, new Investigator(contextManagerFactory).getHandlers()
		);
	}
	
	/**
	 * {@inheritDoc}
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(final ServletContextEvent event) {
		// do nothing
	}
}
