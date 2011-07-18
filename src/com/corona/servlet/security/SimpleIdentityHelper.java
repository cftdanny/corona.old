/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.dbunit.util.Base64;

import com.corona.crypto.CertifiedKey;
import com.corona.crypto.Cypher;
import com.corona.crypto.CypherException;
import com.corona.crypto.CypherFactory;
import com.corona.util.StringUtil;

/**
 * <p>The helper function in order to load user and roles from HTTP request cookie </p>
 *
 * @author $Author$
 * @version $Id$
 */
final class SimpleIdentityHelper {

	/**
	 * the cookie name to store user name 
	 */
	public static final String SECURITY_CRYPT_KEY = "com.corona.security.key";
	
	/**
	 * the cookie name to store user name 
	 */
	public static final String SECURITY_USER_NAME = "com.corona.security.user";
	
	/**
	 * the cookie name to store role names
	 */
	public static final String SECURITY_ROLE_NAME = "com.corona.security.role";
 
	/**
	 * the encryption and decryption algorithm
	 */
	public static final String ALGORITHM = "DES";
	
	/**
	 * the cypher to encrypt and decrypt user and roles
	 */
	private static Cypher cypher;
	
	/**
	 * the key for user cookie
	 */
	private static String userKey;
	
	/**
	 * the key for role cookie
	 */
	private static String roleKey;
	
	/**
	 * utility class
	 */
	private SimpleIdentityHelper() {
		// do nothing
	}
	
	/**
	 * @param servletContext the SERVLET context
	 * @exception Exception if fail to create cypher
	 */
	static void init(final ServletContext servletContext) throws Exception {
		
		// get cookie encrypt and decrypt key from SERVLET context
		String key = servletContext.getInitParameter(SECURITY_CRYPT_KEY);
		if (key == null) {
			throw new NullPointerException("Certified key for cookie identity is not set in SERVLET context");
		}
		
		// parse certificated key loaded from SERVLET context
		String[] keys = key.split(",");
		byte[] bytes = new byte[keys.length];
		
		for (int i = 0, count = keys.length; i < count; i++) {
			bytes[i] = Byte.parseByte(keys[i]);
		}
		
		// create cypher by key
		CertifiedKey certifiedKey = new CertifiedKey(bytes);
		cypher = CypherFactory.get(ALGORITHM).create(certifiedKey);
		
		// get key for store user name in cookie
		userKey = servletContext.getInitParameter(SECURITY_USER_NAME);
		if (StringUtil.isBlank(userKey)) {
			userKey = "corona.user";
		}

		bytes = cypher.encrypt(userKey.getBytes());
		userKey = Base64.encodeBytes(bytes);

		// get key for store role names in cookie
		roleKey = servletContext.getInitParameter(SECURITY_ROLE_NAME);
		if (StringUtil.isBlank(roleKey)) {
			roleKey = "corona.role";
		}
		
		bytes = cypher.encrypt(roleKey.getBytes());
		roleKey = Base64.encodeBytes(bytes);
	}
	
	/**
	 * @param request the request
	 * @param name the cookie name
	 * @return the value
	 * @throws CypherException if fail to decrypt cookie name or value
	 */
	private static String getCookie(final HttpServletRequest request, final String name) throws CypherException {
		
		// find the cookie with specified name
		for (Cookie cookie : request.getCookies()) {
			
			if (name.equals(cookie.getName())) {
					
				byte[] source = Base64.decode(cookie.getValue());
				byte[] target = cypher.decrypt(source);
				
				return new String(target);
			}
		}
		
		// does not find the cookie with specified name
		return null;
	}
	
	/**
	 * @param request the HTTP SERVLET request
	 * @return the user stored in cookie
	 * @throws CypherException if fail to decrypt cookie name or value
	 */
	static User load(final HttpServletRequest request) throws CypherException {
		
		// find user name in cookie, if not find, just return
		String name = getCookie(request, userKey);
		if (name == null) {
			return null;
		}
		
		// if have user, load it with its roles
		User user = new User(name);
		
		String roles = getCookie(request, roleKey);
		if (!StringUtil.isBlank(roles)) {
			for (String role : roles.split(",")) {
				user.getRoles().add(new Role(role));
			}
		}
		
		return user;
	}
}
