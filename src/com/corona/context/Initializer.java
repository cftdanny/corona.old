/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.util.Arrays;

import com.corona.context.spi.ContextManagerFactoryImpl;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>The <b>Initializer</b> is used to create context manager factory ({@link ContextManagerFactory}) 
 * with customized modules. Usually, it is the startup code for framework. After context manager factory 
 * is created, it can be used to create context Manager {@link ContextManager}. And context manager can 
 * be used to resolve component instance, etc. 
 * </p>
 * 
 * <p>Bellow is example code to create <b>Context Manager</b> and then resolve component by this new 
 * context manager: </p>
 * <pre>
 * 	ContextManagerFactory contextManagerFactory = Initializer.create(module1, module2, module3);
 * 	ContextManager contextManager = contextManagerFactory.create();
 * 	Component component = contextManager.get(Component.class);
 * </pre>
 *
 * @author $Author$
 * @version $Id$
 */
public final class Initializer {

	/**
	 * the logger
	 */
	// CHECKSTYLE:OFF
	private static final Log logger = LogFactory.getLog(Initializer.class);
	// CHECKSTYLE:ON
	
	/**
	 * utility class
	 */
	protected Initializer() {
		// do nothing
	}

	/**
	 * <p>Create context manager factory with customized modules. All scopes and components are defined in 
	 * these modules. All modules should implement interface {@link Module} and configure components in it.
	 * </p>
	 * 
	 * @param modules the modules
	 * @return new context manager factory
	 */
	public static ContextManagerFactory build(final Module... modules) {
		return build(Arrays.asList(modules));
	}
	
	/**
	 * <p>Create context manager factory with customized modules. All scopes and components are defined in 
	 * these modules. All modules should implement interface {@link Module} and configure components in it.
	 * </p>
	 * 
	 * @param modules the modules
	 * @return new context manager factory
	 */
	public static ContextManagerFactory build(final Iterable<Module> modules) {
		
		ContextManagerFactoryImpl contextManagerFactory = new ContextManagerFactoryImpl();

		logger.info("Start to initialize context manager factory [{0}]", contextManagerFactory);
		contextManagerFactory.init(modules);
		logger.info("Context manager factory [{0}] has been configured successfully", 
				contextManagerFactory
		); 
		
		return contextManagerFactory;
	}
}
