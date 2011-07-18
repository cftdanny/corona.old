/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>The logged in user </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class User {

	/**
	 * the user name
	 */
	private String name;
	
	/**
	 * the roles of user
	 */
	private List<Role> roles = new ArrayList<Role>();

	/**
	 * default constructor
	 */
	public User() {
		// nothing
	}
	
	/**
	 * @param name the user name
	 */
	public User(final String name) {
		this.name = name;
	}
	
	/**
	 * @return the user name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the user name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}
	
	/**
	 * @return the roles of user
	 */
	public List<Role> getRoles() {
		return roles;
	}
}
