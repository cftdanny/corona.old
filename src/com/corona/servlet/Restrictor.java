/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>The restrictor is used to verify whether resource can be accessed or not </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Restrictor {

	/**
	 * @param path the request path
	 * @param request the HTTP SERVLET request
	 * @param response the HTTP SERVLET response
	 * @return whether can access resource for request path
	 * @throws HandleException if fail to check accessing permission
	 */
	boolean restrict(String path, HttpServletRequest request, HttpServletResponse response) throws HandleException;
}
