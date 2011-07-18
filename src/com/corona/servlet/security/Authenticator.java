/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

/**
 * <p>This authenticator is used to check whether user can log in application or not </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Authenticator {

	/**
	 * <p>check whether user can log in application or not. </p>
	 * 
	 * @return the logged in user or <code>null</code> if fail logged in
	 */
	User authenticate();
}
