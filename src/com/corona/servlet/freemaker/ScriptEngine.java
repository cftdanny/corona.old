/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

/**
 * <p>This FreeMaker script engine is used to compile FreeMaker script into executable object. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ScriptEngine {

	/**
	 * @param name the Velocity script file
	 * @return the compiled and executable FreeMaker script
	 */
	Script compile(String name);
}
