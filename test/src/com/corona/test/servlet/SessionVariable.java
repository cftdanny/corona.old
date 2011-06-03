/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import com.corona.servlet.annotation.Session;

/**
 * <p>The testing session variable </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Session
public class SessionVariable {

	/**
	 * the value
	 */
	private String value = "";
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(final String value) {
		this.value = value;
	}
}
