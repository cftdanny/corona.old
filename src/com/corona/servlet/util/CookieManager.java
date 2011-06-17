/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.util;

import java.util.List;

import javax.servlet.http.Cookie;

/**
 * <p>This manager is used to manager cookies </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface CookieManager {

	/**
	 * @param name the name of cookie
	 * @return the value of cookie or <code>null</code> if does not exists
	 */
	String getValue(String name);

	/**
	 * @param name the name of cookie
	 * @return the cookie or <code>null</code> if does not exists
	 */
	Cookie getCookie(String name);

	/**
	 * @return all cookies
	 */
	List<Cookie> getCookies();
	
	/**
	 * @param name the cookie name
	 * @param value the cookie value
	 * @return the new cookie and its path is context path
	 */
	Cookie createCookie(String name, String value);
	
	/**
	 * @param name the cookie name
	 * @param value the cookie value
	 * @return the new cookie and its path is request path
	 */
	Cookie createPageCookie(String name, String value);

	/**
	 * @param name the cookie name
	 * @param value the cookie value
	 * @return the new cookie and its path is context path
	 */
	Cookie createApplicationCookie(String name, String value);

	/**
	 * @param name the cookie name
	 * @param value the cookie value
	 * @return the new cookie and its path is "/"
	 */
	Cookie createGlobalCookie(String name, String value);

	/**
	 * @param cookie the cookie to be added to response
	 */
	void add(Cookie cookie);
}
