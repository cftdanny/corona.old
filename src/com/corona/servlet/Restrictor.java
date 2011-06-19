/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>This interface is used to test whether web resource can be accessed or not </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Restrictor {

	/**
	 * @param path the request path
	 * @param request the HTTP SERVLET request
	 * @return whether can access resource for request path
	 */
	boolean restrict(String path, HttpServletRequest request);
}
