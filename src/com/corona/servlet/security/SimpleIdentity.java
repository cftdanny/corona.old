/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>This identity will store logged user information to cookie </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SimpleIdentity implements Identity {

	private HttpServletRequest request;
	
	/**
	 * the authenticator for user to log in
	 */
	private Authenticator authenticator;
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.security.Identity#isLoggedIn()
	 */
	@Override
	public boolean isLoggedIn() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.security.Identity#login()
	 */
	@Override
	public User login() {
		
		User user = this.authenticator.authenticate();
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.security.Identity#logout()
	 */
	@Override
	public void logout() {
	}
}
