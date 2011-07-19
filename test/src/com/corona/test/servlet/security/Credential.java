/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.security;

import com.corona.servlet.annotation.Param;
import com.corona.servlet.security.Authenticator;
import com.corona.servlet.security.Role;
import com.corona.servlet.security.User;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Credential implements Authenticator {

	/**
	 * the user name
	 */
	@Param private String username;
	
	/**
	 * the password
	 */
	@Param private String password;

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.security.Authenticator#authenticate()
	 */
	@Override
	public User authenticate() {
		
		if (!this.username.equals(this.password)) {
			return null;
		}
		
		User user = new User(this.username);
		user.getRoles().add(new Role("ADM"));
		return user;
	}
}
