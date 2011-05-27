/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;

/**
 * <p>The utility class for reading and writing cookies </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class CookieUtil {
	
	/**
	 * utility class
	 */
	protected CookieUtil() {
		// do nothing
	}

	/**
	 * @param contextManager the context manager
	 * @return all cookies
	 */
	public static Map<String, Cookie> getCookies(final ContextManager contextManager) {
		
		HttpServletRequest request = contextManager.get(HttpServletRequest.class);
		if (request == null) {
			throw new NullPointerException("HTTP Servlet Request does not register in current context");
		}
		return CookieUtil.getCookies(request);
	}
	
	/**
	 * @param request the HTTP SERVLET request
	 * @return all cookies
	 */
	public static Map<String, Cookie> getCookies(final ServletRequest request) {
		return CookieUtil.getCookies((HttpServletRequest) request);
	}
	
	/**
	 * @param request the HTTP SERVLET request
	 * @return all cookies
	 */
	public static Map<String, Cookie> getCookies(final HttpServletRequest request) {
		
		HashMap<String, Cookie> cookies = new HashMap<String, Cookie>();
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				cookies.put(cookie.getName(), cookie);
			}
		}
		return cookies;
	}

	/**
	 * @param contextManager the context manager
	 * @param name the cookie name
	 * @return the value of cookie or <code>null</code> if does not exist
	 */
	public static Cookie getCookie(final ContextManager contextManager, final String name) {
		
		HttpServletRequest request = contextManager.get(HttpServletRequest.class);
		if (request == null) {
			throw new NullPointerException("HTTP Servlet Request does not register in current context");
		}
		return CookieUtil.getCookie(request, name);
	}

	/**
	 * @param request the HTTP SERVLET request
	 * @param name the cookie name
	 * @return the value of cookie or <code>null</code> if does not exist
	 */
	public static Cookie getCookie(final ServletRequest request, final String name) {
		return CookieUtil.getCookie((HttpServletRequest) request, name);
	}

	/**
	 * @param request the HTTP SERVLET request
	 * @param name the cookie name
	 * @return the value of cookie or <code>null</code> if does not exist
	 */
	public static Cookie getCookie(final HttpServletRequest request, final String name) {
		
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * @param contextManager the context manager
	 * @param name the cookie name
	 * @return the value of cookie or <code>null</code> if does not exist
	 */
	public static String getValue(final ContextManager contextManager, final String name) {
		
		HttpServletRequest request = contextManager.get(HttpServletRequest.class);
		if (request == null) {
			throw new NullPointerException("HTTP Servlet Request does not register in current context");
		}
		return CookieUtil.getValue(request, name);
	}

	/**
	 * @param request the HTTP SERVLET request
	 * @param name the cookie name
	 * @return the value of cookie or <code>null</code> if does not exist
	 */
	public static String getValue(final ServletRequest request, final String name) {
		return CookieUtil.getValue((HttpServletRequest) request, name);
	}
	
	/**
	 * @param request the HTTP SERVLET request
	 * @param name the cookie name
	 * @return the value of cookie or <code>null</code> if does not exist
	 */
	public static String getValue(final HttpServletRequest request, final String name) {
		
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		
		return null;
	}

	/**
	 * @param contextManager the context manager
	 * @param name the cookie name
	 * @param value the value of cookie
	 */
	public static void setValue(final ContextManager contextManager, final String name, final String value) {

		HttpServletResponse response = contextManager.get(HttpServletResponse.class);
		if (response == null) {
			throw new NullPointerException("HTTP Servlet Response does not register in current context");
		}
		CookieUtil.setValue(response, name, value);
	}

	/**
	 * @param response the HTTP SERVLET response
	 * @param name the cookie name
	 * @param value the value of cookie
	 */
	public static void setValue(final ServletResponse response, final String name, final String value) {
		CookieUtil.setValue((HttpServletResponse) response, name, value);
	}
	
	/**
	 * @param response the HTTP SERVLET response
	 * @param name the cookie name
	 * @param value the value of cookie
	 */
	public static void setValue(final HttpServletResponse response, final String name, final String value) {
		
		Cookie cookie = new Cookie(name, value);
		response.addCookie(cookie);
	}
}
