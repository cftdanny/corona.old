/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.corona.context.annotation.Optional;
import com.corona.servlet.annotation.Param;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.WebResource;
import com.corona.servlet.annotation.Xml;

/**
 * <p>This producer is used to create XML </p>
 *
 * @author $Author$
 * @version $Id$
 */
@WebResource
@XmlRootElement(name = "result")
public class IndexXml {

	/**
	 * name
	 */
	@Param("username") @Optional private String username = "shanghai";
	
	/**
	 * password
	 */
	@Param("password") @Optional private String password = "123456";

	/**
	 * @return the user name
	 */
	@XmlElement(name = "user") public String getUsername() {
		return username;
	}

	/**
	 * @param username the user name to set
	 */
	public void setUsername(final String username) {
		this.username = username;
	}
	
	/**
	 * @return the password
	 */
	@XmlElement(name = "password") public String getPassword() {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @return the user
	 */
	@Same("/index.xml")
	@Xml public IndexXml produce() {
		return this;
	}
}
