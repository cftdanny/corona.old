/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import java.io.Serializable;

import com.corona.servlet.annotation.Session;

/**
 * <p>The testing session variable </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Session
public class SessionVariable implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1632842602508062001L;
	
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
