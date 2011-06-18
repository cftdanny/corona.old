/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.component.cookie;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;
import com.corona.context.annotation.Inject;
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
	private static final String ALGORITHM = "DES";
	
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
			14, -2, -53, 88, -118, 56, -116, -23
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
	 * <p>This main is used to create DES key, only for testing purpose </p>
	 * 
	 * @param args the argument of command line
	 * @exception Exception if fail
	 */
	public static void main(final String[] args) throws Exception {
	
		KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
		generator.init(new SecureRandom());
		SecretKey key = generator.generateKey();
		for (byte b : key.getEncoded()) {
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
	 * @param cookie the cookie to be encoded
	 * @return the encoded cookie
	 */
	private Cookie encrypt(final Cookie cookie) {
		
		if ((cookie != null) && (cookie.getValue() != null)) {
			
			try {
				// create decoding cipher if it isn't created yet
				if (this.encoder == null) {

					this.encoder = Cipher.getInstance(ALGORITHM);
					
					DESKeySpec desKeySpec = new DESKeySpec(this.secretKey);
					SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(ALGORITHM);
					this.encoder.init(Cipher.ENCRYPT_MODE, keyfactory.generateSecret(desKeySpec));
				}
				
				byte[] bytes = this.encoder.doFinal(cookie.getValue().getBytes());
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
				// create decoding cipher if it isn't created yet
				if (this.decoder == null) {
					
					this.decoder = Cipher.getInstance(ALGORITHM);
					
					DESKeySpec desKeySpec = new DESKeySpec(this.secretKey);
					SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(ALGORITHM);
					this.decoder.init(Cipher.DECRYPT_MODE, keyfactory.generateSecret(desKeySpec));
				}
				
				byte[] bytes = this.decoder.doFinal(Base64.decode(cookie.getValue()));
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
