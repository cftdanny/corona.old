/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

/**
 * <p>The identity is used to log in application by authenticator </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Identity {

	/**
	 * @return logged in user or <code>null</code> if not logged in
	 */
	User getUser();
	
	/**
	 * @return whether current user is logged in
	 */
	boolean isLoggedIn();
	
	/**
	 * log in application with authenticator
	 * 
	 * @return the logged in user or <code>null</code> if failed
	 */
	User login();
	
	/**
	 * log out user from application
	 */
	void logout();
}
