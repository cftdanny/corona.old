/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import java.util.Properties;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class LogFactoryProxy {

	/**
	 * the testing Java Logging configuration file
	 */
	private static final String TESTING = "/logging-test.properties";
	
	/**
	 * the production Java Logging configuration file
	 */
	private static final String RUNTIME = "/logging.properties";

	/**
	 * the log factory instance
	 */
	private static LogFactory instance;
	
	/**
	 * initial log factory
	 */
	static {
		
	}
	
	/**
	 * @param config
	 */
	public abstract void configure(final Properties properties);

	/**
	 * <p>reset log factory from configuration file </p>
	 */
	public abstract void reset();
	
	/**
	 * <p>close log factory </p>
	 */
	public abstract void close();
	
	/**
	 * @param clazz the class name
	 * @return the new log
	 */
	public abstract Log create(final Class<?> clazz);
	
	/**
	 * @param name the log name
	 * @return the new log
	 */
	public abstract Log create(final String name);
	
	public static Log getLog(final Class<?> clazz) {
		return null;
	}
}
