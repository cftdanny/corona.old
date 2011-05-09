/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.velocity;

/**
 * <p>This Velocity script engine is used to compile Velocity script into executable object. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ScriptEngine {

	/**
	 * @param name the Velocity script file
	 * @param encoding the character encoding for script
	 * @return the compiled and executable velocity script
	 */
	Script compile(String name, String encoding);
}
