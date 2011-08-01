/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>This interface is used to test whether web resource can be matched or not for URL </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Selector {

	/**
	 * @param path the request path
	 * @param request the HTTP SERVLET request
	 * @return whether can access resource for request path
	 */
	boolean select(String path, HttpServletRequest request);
}
