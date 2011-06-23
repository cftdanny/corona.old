/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.io.jaxb;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}
}
