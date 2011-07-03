/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.remote;

import com.corona.servlet.annotation.Controller;
import com.corona.servlet.annotation.Remote;
import com.corona.servlet.annotation.Same;

/**
 * <p>The remote testing </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class DemoService {

	/**
	 * @param username user name
	 * @param password password
	 * @return user name as token
	 */
	@Same("/login")
	@Remote
	public String login(final String username, final String password) {
		return username;
	}
	
	/**
	 * @param token the token
	 */
	@Same("/logout")
	@Remote
	public void logout(final String token) {
		
	}
}
