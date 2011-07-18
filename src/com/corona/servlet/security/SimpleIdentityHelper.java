/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class SimpleIdentityHelper {

	/**
	 * whether enable simple identity security control
	 */
	public static final String SECURITY_ENABLED = "com.corona.security";
	
	/**
	 * the cookie name to store user name 
	 */
	public static final String SECURITY_CRYPT_KEY = "com.corona.security.key";
	
	/**
	 * the cookie name to store user name 
	 */
	public static final String SECURITY_USER_NAME = "com.corona.security.user";
	
	/**
	 * the cookie name to store role names
	 */
	public static final String SECURITY_ROLE_NAME = "com.corona.security.role";
 
	/**
	 * utility class
	 */
	private SimpleIdentityHelper() {
		// do nothing
	}
	
	/**
	 * @param request the HTTP SERVLET request
	 * @return the user name stored in cookie
	 */
	public static String getUser(final HttpServletRequest request) {
		return null;
	}
	
	/**
	 * @param request the HTTP SERVLET request
	 * @return the role names stored in cookie
	 */
	public static List<String> getRoles(final HttpServletRequest request) {
		return null;
	}
}
