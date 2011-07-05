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
	
	/**
	 * @param source source
	 * @return the result
	 */
	@Same("/calculate")
	@Remote
	public Result calculate(final Source source) {
		
		Result result = new Result();
		result.setC(source.getA() + source.getB());
		result.setD(source.getA() - source.getB());
		
		return result;
	}
}
