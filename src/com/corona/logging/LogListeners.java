/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.corona.util.StringUtil;

/**
 * <p>The log listeners </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class LogListeners {

	/**
	 * the head of listener key
	 */
	private static final String LISTENER = "com.corona.logging.listener.";

	/**
	 * the log listener property key for configuration
	 */
	public static final String LISTENER_PROPERTY_KEY = "com.corona.logging.";

	/**
	 * all log listeners that is defined in configuration file
	 */
	private static List<LogListener> listeners = new ArrayList<LogListener>();
	
	/**
	 * utility class
	 */
	private LogListeners() {
		// do nothing
	}
	
	/**
	 * @param resource logging configuration file
	 */
	@SuppressWarnings("static-access")
	static void configure(final String resource) {
		
		Properties properties = new Properties();
		try {
			properties.load(LogListeners.class.getResourceAsStream(resource));
		} catch (Exception e) {
			return;
		}
		
		// find listener class from all keys
		for (Object key : properties.keySet()) {
			
			String className = null;
			if (key.toString().startsWith(LISTENER)) {
				className = properties.getProperty(key.toString());
			}

			// load and create listener instance, configure it, and register it 
			if (className != null) {
				try {
					LogListener listener = (LogListener) LogListeners.class.forName(className).newInstance();
					listener.configure(properties);
					listeners.add(listener);
				} catch (Exception e) {
					// skip this exception, did do nothing
					className = null;
				}
			}
		}
	}
	
	/**
	 * close all log listeners
	 */
	static void close() {
		
		for (LogListener listener : listeners) {
			listener.close();
		}
	}
	
	/**
	 * @param pattern the pattern that is used to formatted to logging message with arguments.
	 * @param args the values of arguments for parameter pattern.
	 * @return the formated message
	 */
	public static String log(final String pattern, final Object[] args) {
		
		String message = StringUtil.format(pattern, args);
		for (LogListener listener : listeners) {
			listener.log(message);
		}
		return message;
	}
	
	/**
	 * @param pattern the pattern that is used to formatted to logging message with arguments.
	 * @param cause the error exception
	 * @param args the values of arguments for parameter pattern.
	 * @return the formated message
	 */
	public static String log(final String pattern, final Throwable cause, final Object[] args) {
		
		String message = StringUtil.format(pattern, args);
		for (LogListener listener : listeners) {
			listener.log(message, cause);
		}
		return message;
	}
}
