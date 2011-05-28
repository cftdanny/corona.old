/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>The matcher is used to match HTTP request URI with pattern and return matched result. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Matcher {

	/**
	 * @return the match priority
	 */
	int getPriority();
	
	/**
	 * <p>Match HTTP request URI and return matched result. </p>
	 * 
	 * @param request the HTTP request
	 * @return the matched result
	 */
	MatchResult match(HttpServletRequest request);
}
