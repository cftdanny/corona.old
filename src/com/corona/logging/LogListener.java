/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import java.util.Properties;

/**
 * <p>A listener to listen logging event </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface LogListener {

	/**
	 * @param properties the properties to configure log listener
	 * @throws Exception if fail to configure log listener
	 */
	void configure(Properties properties) throws Exception;
	
	/**
	 * close this listener
	 */
	void close();
	
	/**
	 * @param message the message to be listened
	 */
	void log(String message);
	
	/**
	 * @param message the message to be listened
	 * @param cause the error exception
	 */
	void log(String message, Throwable cause);
}
