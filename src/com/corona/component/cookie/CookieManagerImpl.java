/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.component.cookie;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;
import com.corona.context.annotation.Inject;
import com.corona.crypto.CipherEngine;
import com.corona.crypto.CipherEngineFactory;
import com.corona.crypto.CipherException;
import com.corona.crypto.Key;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.util.Base64;
import com.corona.util.StringUtil;

/**
 * <p>The default implementation of {@link CookieManager} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class CookieManagerImpl implements CookieManager {

	/**
	 * the encrypt and decrypt algorithm
	 */
	private static final String ALGORITHM = CipherEngineFactory.RC4;
	
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
	private String domain = "";
	
	/**
	 * the default max age of new created cookie
	 */
	private Integer maxAge = null;
	
	/**
	 * the default secret key for algorithm
	 */
	private byte[] secretKey = new byte[] {
			-107, 108, 89, 59, -123, 11, 127, -44, 65, -19, 57, 62, -59, -126, 11, -92
	};
	
	/**
	 * the cipher engine
	 */
	private CipherEngine cipherEngine;
	
	/**
	 * <p>This main is used to create DES key, only for testing purpose </p>
	 * 
	 * @param args the argument of command line
	 * @exception Exception if fail
	 */
	public static void main(final String[] args) throws Exception {
	
		CipherEngine cipherEngine = CipherEngineFactory.create(ALGORITHM);
		Key key = cipherEngine.generateKey();

		System.out.println("Public Key:");
		System.out.print("\t");
		for (byte b : key.getEncryptionKey()) {
			System.out.print(b);
			System.out.print(", ");
		}
		System.out.println();
		
		System.out.println("Private Key:");
		System.out.print("\t");
		for (byte b : key.getDecryptionKey()) {
			System.out.print(b);
			System.out.print(", ");
		}
		System.out.println();
	}

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
	 * @return the secret key
	 */
	public byte[] getSecretKey() {
		return secretKey;
	}
	
	/**
	 * @param secretKey the secret key to set
	 */
	public void setSecretKey(final byte[] secretKey) {
		this.secretKey = secretKey;
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
	 * @return the cipher engine to encrypt or decrypt data
	 * @throws CipherException if fail to create cipher engine
	 */
	private CipherEngine getCipherEngine() throws CipherException {
		
		if (this.cipherEngine == null) {
			this.cipherEngine = CipherEngineFactory.create(ALGORITHM);
			
			this.cipherEngine.setEncryptKey(this.secretKey);
			this.cipherEngine.setDecryptKey(this.secretKey);
		}
		return this.cipherEngine;
	}
	
	/**
	 * @param cookie the cookie to be encoded
	 * @return the encoded cookie
	 */
	private Cookie encrypt(final Cookie cookie) {
		
		if ((cookie != null) && (cookie.getValue() != null)) {
			
			try {
				byte[] bytes = this.getCipherEngine().encrypt(cookie.getValue().getBytes());
				cookie.setValue(Base64.encodeBytes(bytes));
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
				byte[] bytes = this.getCipherEngine().decrypt(Base64.decode(cookie.getValue()));
				cookie.setValue(new String(bytes));
			} catch (Exception e) {
				this.logger.error("Fail to decode cookie, just skip this exception", e);
			}
		}
		
		return cookie;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.component.cookie.CookieManager#getValue(java.lang.String)
	 */
	@Override
	public String getValue(final String name) {
		
		Cookie cookie = this.getCookie(name);
		return cookie == null ? null : cookie.getValue();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.component.cookie.CookieManager#getCookie(java.lang.String)
	 */
	@Override
	public Cookie getCookie(final String name) {
		
		Cookie[] cookies = this.getServletRequest().getCookies();
		if (cookies != null) {
			
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return this.decrypt(cookie);
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.component.cookie.CookieManager#getCookies()
	 */
	@Override
	public List<Cookie> getCookies() {
		
		Cookie[] cookies = this.getServletRequest().getCookies();
		if (cookies == null) {
			return new ArrayList<Cookie>();
		}
			
		List<Cookie> result = new ArrayList<Cookie>();
		for (Cookie cookie : cookies) {
			result.add(this.decrypt(cookie));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.component.cookie.CookieManager#createCookie(java.lang.String, java.lang.String)
	 */
	@Override
	public Cookie createCookie(final String name, final String value) {
		return this.createApplicationCookie(name, value);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.component.cookie.CookieManager#createPageCookie(java.lang.String, java.lang.String)
	 */
	@Override
	public Cookie createPageCookie(final String name, final String value) {

		Cookie cookie = new Cookie(name, value);
		if (!StringUtil.isBlank(this.domain)) {
			cookie.setDomain(this.domain);
		}
		cookie.setPath(this.getServletRequest().getPathInfo());
		if (this.maxAge != null) {
			cookie.setMaxAge(this.maxAge);
		}
		return this.encrypt(cookie);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.component.cookie.CookieManager#createApplicationCookie(java.lang.String, java.lang.String)
	 */
	@Override
	public Cookie createApplicationCookie(final String name, final String value) {

		Cookie cookie = new Cookie(name, value);
		if (!StringUtil.isBlank(this.domain)) {
			cookie.setDomain(this.domain);
		}
		cookie.setPath(this.getServletContext().getContextPath());
		if (this.maxAge != null) {
			cookie.setMaxAge(this.maxAge);
		}
		return this.encrypt(cookie);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.component.cookie.CookieManager#createGlobalCookie(java.lang.String, java.lang.String)
	 */
	@Override
	public Cookie createGlobalCookie(final String name, final String value) {
		
		Cookie cookie = new Cookie(name, value);
		if (!StringUtil.isBlank(this.domain)) {
			cookie.setDomain(this.domain);
		}
		cookie.setPath("/");
		if (this.maxAge != null) {
			cookie.setMaxAge(this.maxAge);
		}
		return this.encrypt(cookie);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.component.cookie.CookieManager#add(javax.servlet.http.Cookie)
	 */
	@Override
	public void add(final Cookie cookie) {
		this.getServletResponse().addCookie(cookie);
	}
}
