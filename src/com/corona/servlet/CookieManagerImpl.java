/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;
import com.corona.context.annotation.Inject;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.util.StringUtil;

/**
 * <p>The default implementation of {@link CookieManager} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class CookieManagerImpl implements CookieManager {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(CookieManagerImpl.class);
	
	/**
	 * the current context manager
	 */
	@Inject private ContextManager contextManager;
	
	/**
	 * the domain of cookie
	 */
	private String domain;
	
	/**
	 * the default max age of new created cookie
	 */
	private Integer maxAge = 14 * 24 * 60 * 60;
	
	/**
	 * the default secret key for algorithm
	 */
	private byte[] secretKey = new byte[] {
			-80, 112, -27, -45, -118, 88, -95, 59
	};
	
	/**
	 * the cipher to encrypt cookie value
	 */
	private Cipher encoder;
	
	/**
	 * the cipher to decode cookie value
	 */
	private Cipher decoder;
	
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
	 * @return the default max age of new created cookie
	 */
	public Integer getMaxAge() {
		return maxAge;
	}

	/**
	 * @param maxAge the default max age of new created cookie to set
	 */
	public void setMaxAge(final Integer maxAge) {
		this.maxAge = maxAge;
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
	 * @param cookie the cookie to be encoded
	 * @return the encoded cookie
	 */
	private Cookie encrypt(final Cookie cookie) {
		
		if ((cookie != null) && (cookie.getValue() != null)) {
			
			try {
				// create decoding cipher if it isn't created yet
				if (this.encoder == null) {
					this.encoder = Cipher.getInstance("DES");
					
					KeySpec spec = new SecretKeySpec(this.secretKey, "DES");
					SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(spec);
					this.encoder.init(Cipher.ENCRYPT_MODE, key);
				}
				
				byte[] bytes = this.encoder.doFinal(cookie.getValue().getBytes());
				cookie.setValue(new String(bytes));
			} catch (Exception e) {
				this.logger.error("Fail to decode cookie, just skip this exception", e);
			}
		}
		
		return cookie;
	}

	/**
	 * @param cookie the cookie to be decoded
	 * @return the decoded cookie
	 */
	private Cookie decrypt(final Cookie cookie) {
		
		if ((cookie != null) && (cookie.getValue() != null)) {
			
			try {
				// create decoding cipher if it isn't created yet
				if (this.decoder == null) {
					this.decoder = Cipher.getInstance("DES");
					
					KeySpec spec = new SecretKeySpec(this.secretKey, "DES");
					SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(spec);
					this.decoder.init(Cipher.DECRYPT_MODE, key);
				}
				
				byte[] bytes = this.decoder.doFinal(cookie.getValue().getBytes());
				cookie.setValue(new String(bytes));
			} catch (Exception e) {
				this.logger.error("Fail to decode cookie, just skip this exception", e);
			}
		}
		
		return cookie;
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
					return this.decrypt(cookie);
				} else if (contextPath.equals(cookie.getName())) {
					applicationCookie = cookie;
				} else if ("/".equals(cookie.getPath())) {
					globalCookie = cookie;
				}
			}
		}
		return this.decrypt((applicationCookie != null) ? applicationCookie : globalCookie);
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
				result.add(this.decrypt(cookie));
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
				result.add(this.decrypt(cookie));
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
		cookie.setMaxAge(this.maxAge);
		return this.encrypt(cookie);
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
		this.setMaxAge(this.maxAge);
		return this.encrypt(cookie);
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
		cookie.setMaxAge(this.maxAge);
		return this.encrypt(cookie);
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
