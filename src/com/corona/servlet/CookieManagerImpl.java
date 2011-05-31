/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;
import com.corona.context.annotation.Inject;
import com.corona.util.StringUtil;

/**
 * <p>The default implementation of {@link CookieManager} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class CookieManagerImpl implements CookieManager {

	/**
	 * the current context manager
	 */
	@Inject private ContextManager contextManager;
	
	/**
	 * the domain of cookie
	 */
	private String domain;
	
	/**
	 * @return the domain of cookie
	 */
	public String getDomain() {
		return domain;
	}
	
	/**
	 * @param domain the domain of cookie to set
	 */
	public void setDomain(final String domain) {
		this.domain = domain;
	}

	/**
	 * @return the SERVLET context
	 */
	private ServletContext getServletContext() {
		return this.contextManager.get(ServletContext.class);
		
	}
	/**
	 * @return the current SERVLET request
	 */
	private HttpServletRequest getServletRequest() {
		return this.contextManager.get(HttpServletRequest.class);
	}
	
	/**
	 * @return the current SERVLET response
	 */
	private HttpServletResponse getServletResponse() {
		return this.contextManager.get(HttpServletResponse.class);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.CookieManager#getValue(java.lang.String)
	 */
	@Override
	public String getValue(final String name) {
		
		Cookie cookie = this.getCookie(name);
		return cookie == null ? null : cookie.getValue();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.CookieManager#getCookie(java.lang.String)
	 */
	@Override
	public Cookie getCookie(final String name) {
		
		Cookie[] cookies = this.getServletRequest().getCookies();
		if (cookies == null) {
			return null;
		}

		String requestPath = this.getServletRequest().getPathInfo();
		String contextPath = this.getServletContext().getContextPath();
		
		Cookie globalCookie = null, applicationCookie = null;
		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName()) && this.isSameDomain(cookie)) {
				
				if (requestPath.equals(cookie.getPath())) {
					return cookie;
				} else if (contextPath.equals(cookie.getName())) {
					applicationCookie = cookie;
				} else if ("/".equals(cookie.getPath())) {
					globalCookie = cookie;
				}
			}
		}
		return (applicationCookie != null) ? applicationCookie : globalCookie;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.CookieManager#getPageCookie(java.lang.String)
	 */
	@Override
	public Cookie getPageCookie(final String name) {

		String path = this.getServletRequest().getPathInfo();
		for (Cookie cookie : this.getCookies(name)) {
			if (path.equals(cookie.getPath())) {
				return cookie;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.CookieManager#getApplicationCookie(java.lang.String)
	 */
	@Override
	public Cookie getApplicationCookie(final String name) {

		String path = this.getServletContext().getContextPath();
		for (Cookie cookie : this.getCookies(name)) {
			if (path.equals(cookie.getPath())) {
				return cookie;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.CookieManager#getGlobalCookie(java.lang.String)
	 */
	@Override
	public Cookie getGlobalCookie(final String name) {
		
		for (Cookie cookie : this.getCookies(name)) {
			if ("/".equals(cookie.getPath())) {
				return cookie;
			}
		}
		return null;
	}

	/**
	 * @param cookie the cookie
	 * @return whether cookie is matched with domain
	 */
	private boolean isSameDomain(final Cookie cookie) {
		return (StringUtil.isBlank(this.domain) || this.domain.equals(cookie.getPath()));
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.CookieManager#getCookies(java.lang.String)
	 */
	@Override
	public List<Cookie> getCookies(final String name) {
		
		Cookie[] cookies = this.getServletRequest().getCookies();
		if (cookies == null) {
			return new ArrayList<Cookie>();
		}
			
		List<Cookie> result = new ArrayList<Cookie>();
		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName()) && this.isSameDomain(cookie)) {
				result.add(cookie);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.CookieManager#getCookies()
	 */
	@Override
	public List<Cookie> getCookies() {
		
		Cookie[] cookies = this.getServletRequest().getCookies();
		if (cookies == null) {
			return new ArrayList<Cookie>();
		}
			
		List<Cookie> result = new ArrayList<Cookie>();
		for (Cookie cookie : cookies) {
			if (this.isSameDomain(cookie)) {
				result.add(cookie);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.CookieManager#createCookie(java.lang.String, java.lang.String)
	 */
	@Override
	public Cookie createCookie(final String name, final String value) {
		return this.createApplicationCookie(name, value);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.CookieManager#createPageCookie(java.lang.String, java.lang.String)
	 */
	@Override
	public Cookie createPageCookie(final String name, final String value) {

		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(this.domain);
		cookie.setPath(this.getServletRequest().getPathInfo());
		return cookie;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.CookieManager#createApplicationCookie(java.lang.String, java.lang.String)
	 */
	@Override
	public Cookie createApplicationCookie(final String name, final String value) {

		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(this.domain);
		cookie.setPath(this.getServletContext().getContextPath());
		return cookie;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.CookieManager#createGlobalCookie(java.lang.String, java.lang.String)
	 */
	@Override
	public Cookie createGlobalCookie(final String name, final String value) {
		
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(this.domain);
		cookie.setPath("/");
		return cookie;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.CookieManager#add(javax.servlet.http.Cookie)
	 */
	@Override
	public void add(final Cookie cookie) {
		this.getServletResponse().addCookie(cookie);
	}
}
