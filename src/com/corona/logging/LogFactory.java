/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * <p>This logger factory is used to create logger in application. It will create a logger by, 
 * for example, <b>log4j</b>, <b>slf4j</b>, <b>jdk</b> and delegate it to {@link Log}. </p>
 * 
 * <p>Currently, it uses <b>slf4j</b> as backbone logging provider. If it needs to change 
 * to other logging provider later, only changes implementation of {@link LogFactory} and 
 * {@link Log} and keep their protocol same, the logging consumers needn't change anything. </p>
 * 
 * <p>Logger can be created by a class or logger name. For example:
 * <pre>{@code
 * private static final Log logger = LogFactory.getLog(Test.class);
 * private static final Log logger = LogFactory.getLog("name");
 * }</pre> 
 * </p> 
 *
 * @since 1.0, 8/16/2010
 * @author $Author$
 * @version $Id$
 */
public class LogFactory {

	/**
	 * the testing Java Logging configuration file
	 */
	private static final String TESTING = "/test-logging.properties";
	
	/**
	 * the production Java Logging configuration file
	 */
	private static final String RUNTIME = "/logging.properties";
	
	/**
	 * the flag to start GTalk logging
	 */
	private static final String GTALK = GTalkHandler.class.getName() + ".enabled";
	
	/**
	 * the XMPP (Messenger) handler
	 */
	private static GTalkHandler defaultHandler = null;
	
	/**
	 * load Java Logging Configuration from properties file
	 */
	static { 
		
		config(LogManager.getLogManager()); 
		if (LogManager.getLogManager().getProperty(GTALK) != null) {
			defaultHandler = new GTalkHandler();
		}
	}

	/**
	 * the utility class
	 */
	protected LogFactory() {
		// do nothing
	}

	/**
	 * configure Java Logging by properties file 
	 * @param logManager the current log manager
	 */
	private static void config(final LogManager logManager) {
		
		// try to load logging configuration by testing and runtime in sequence
		if (!(config(logManager, TESTING) || config(logManager, RUNTIME))) {
			logManager.reset();
		}
	}
	
	/**
	 * @param logManager the log manager
	 * @param resource the Java Logging configuration file
	 * @return whether Java Logging has been configured
	 */
	private static boolean config(final LogManager logManager, final String resource) {
		
		boolean configured = true;
		try {
			logManager.readConfiguration(
					LogFactory.class.getResourceAsStream(resource)
			);
			Logger.getLogger(LogFactory.class.getName()).info(
					"Configure Java Logging by class path properties file: " + resource
			);
		} catch (Throwable e) {
			configured = false;
		}
		return configured;
	}
	
	/**
	 * <p>create logger by a class. Usually, it is used in a class to log message for this class. For example:
	 * <pre>
	 * public class Test {
	 * 	private static final Log logger = LogFactory.getLog(Test.class);
	 * 	...
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @param clazz the class for the new logger
	 * @return the new logger
	 */
	public static Log getLog(final Class<?> clazz) {
		return getLog(Logger.getLogger(clazz.getName()));
	}
	
	/**
	 * <p>create logger by a logger name. For example:
	 * <pre>
	 * public class Test {
	 * 	private static final Log logger = LogFactory.getLog(Test.class);
	 * 	...
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @param name the logger name
	 * @return the new logger
	 */
	public static Log getLog(final String name) {
		return getLog(Logger.getLogger(name));
	}
	
	/**
	 * @param logger the logger of Java Logging logger
	 * @return the log
	 */
	private static Log getLog(final Logger logger) {
		
		if (defaultHandler != null) {
			logger.addHandler(defaultHandler);
		}
		return new Log(logger);
	}
}
