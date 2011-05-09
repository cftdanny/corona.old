/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>This generator will generate HTTP response according to matched request URL and content producer. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Handler {

	/**
	 * @return the matcher to match request URL
	 */
	Matcher getMatcher();
	
	/**
	 * <p>Create HTTP response according to HTTP request with matched result. </p>
	 *  
	 * @param result the matched result
	 * @param request HTTP SERVLET request
	 * @param response HTTP SERVLET response
	 * @exception HandleException if fail to handle HTTP request
	 */
	void handle(MatchResult result, HttpServletRequest request, HttpServletResponse response) throws HandleException;
}
