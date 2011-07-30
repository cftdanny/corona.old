/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corona.util.StringUtil;

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
	private static final String TESTING = "/logging-test.properties";
	
	/**
	 * the production Java Logging configuration file
	 */
	private static final String RUNTIME = "/logging.properties";
	
	/**
	 * the class name of log manager
	 */
	private static final String LOG_MANAGER_CLASS = "com.corona.logging.LogManager";
	
	/**
	 * the logger
	 */
	private static Log log = LogFactory.getLog(LogFactory.class);
	
	/**
	 * load Java Logging Configuration from properties file
	 */
	static { 
		
		// configure log factory and log listeners
		if (config(TESTING)) {
			LogListeners.configure(TESTING);
		} else if (config(RUNTIME)) {
			LogListeners.configure(RUNTIME);
		}
	}

	/**
	 * the utility class
	 */
	protected LogFactory() {
		// do nothing
	}
	
	/**
	 * @param resource logging configuration properties file
	 * @return whether log factory has been configured by resource
	 */
	private static boolean config(final String resource) {
		
		// load logging configuration properties by resource name
		Properties properties = new Properties();
		try {
			properties.load(LogFactory.class.getResourceAsStream(resource));
		} catch (Exception e) {
			return false;
		}
		
		// find log manager from configuration properties files
		String className = properties.getProperty(LOG_MANAGER_CLASS);
		if (!StringUtil.isBlank(className)) {
			try {
				Class<?> logManagerClass = LogFactory.class.getClassLoader().loadClass(className);
				((LogManager) logManagerClass.newInstance()).configure(resource);
			} catch (Exception e) {
				log.error("Fail to configure logging with [{0}]", e, className);
			}
		}
		return true;
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
		return getLog(LoggerFactory.getLogger(clazz.getName()));
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
		return getLog(LoggerFactory.getLogger(name));
	}
	
	/**
	 * @param logger the logger of Java Logging logger
	 * @return the log
	 */
	private static Log getLog(final Logger logger) {
		return new Log(logger);
	}
	
	/**
	 * close all opened handlers if required. Usually, it logs out from IM server
	 */
	public void close() {
		LogListeners.close();
	}
}
