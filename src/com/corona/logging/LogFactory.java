/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import org.slf4j.LoggerFactory;

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
	 * the utility class
	 */
	protected LogFactory() {
		// do nothing
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
	public static Log getLog(@SuppressWarnings("rawtypes") final Class clazz) {
		return new Log(LoggerFactory.getLogger(clazz));
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
		return new Log(LoggerFactory.getLogger(name));
	}
}
