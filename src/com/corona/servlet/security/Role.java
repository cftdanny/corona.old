/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

/**
 * <p>The role of logged in user </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Role {

	/**
	 * the role name
	 */
	private String name;

	/**
	 * default constructor
	 */
	public Role() {
		// do nothing
	}
	
	/**
	 * @param name the role name
	 */
	public Role(final String name) {
		this.name = name;
	}
	
	/**
	 * @return the role name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the role name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}
}
