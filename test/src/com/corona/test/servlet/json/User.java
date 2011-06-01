/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.json;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
@XmlRootElement
public class User {

	/**
	 * the name
	 */
	private String name;
	
	/**
	 * the password
	 */
	private String password;
	
	/**
	 * do nothing
	 */
	public User() {
		// noting
	}
	
	/**
	 * @param name the name
	 * @param password the password
	 */
	public User(final String name, final String password) {
		this.name = name;
		this.password = password;
	}
	
	/**
	 * @return the name
	 */
	@XmlElement public String getName() {
		return name;
	}

	/**
	 * @return the password
	 */
	@XmlElement public String getPassword() {
		return password;
	}
}
