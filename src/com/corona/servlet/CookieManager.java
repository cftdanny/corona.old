/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.util.List;

import javax.servlet.http.Cookie;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface CookieManager {

	
	String getValue(String name);
	
	String getValues(String name);
	
	Cookie getCookie(String name);
	
	List<Cookie> getCookies(String name);
	
	List<Cookie> getCookies();
	
	Cookie create(String name, String value);
	
	void add(Cookie cookie);
	
	void add(String name, String value);
	
	void add(String path, String name, String value);
}
