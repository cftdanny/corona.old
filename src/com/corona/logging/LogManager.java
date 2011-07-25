/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

/**
 * <p>Configure logging framework that is used in application by properties </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface LogManager {

	/**
	 * @param resource the resource name of logging configuration in class path
	 */
	void configure(String resource);

	/**
	 * <p>reset log manager configuration </p>
	 */
	void reset();
}
