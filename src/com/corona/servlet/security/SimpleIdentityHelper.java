/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import javax.servlet.ServletConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.crypto.CertifiedKey;
import com.corona.crypto.Cypher;
import com.corona.crypto.CypherException;
import com.corona.crypto.CypherFactory;
import com.corona.util.Base64;
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
	public static final String SECURITY_DOMAIN_NAME = "com.corona.security.domain";

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
	 * the domain name
	 */
	private static String domain;
	
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
	 * @param servletConfig the SERVLET configuration
	 * @exception Exception if fail to create cypher
	 */
	static void init(final ServletConfig servletConfig) throws Exception {
		
		// get cookie encrypt and decrypt key from SERVLET context
		String key = servletConfig.getInitParameter(SECURITY_CRYPT_KEY);
		if (key == null) {
			throw new NullPointerException("Certified key for cookie identity is not set in SERVLET context");
		}
		
		// parse certificated key loaded from SERVLET context
		String[] keys = key.split(",");
		byte[] bytes = new byte[keys.length];
		
		for (int i = 0, count = keys.length; i < count; i++) {
			bytes[i] = Byte.parseByte(keys[i].trim());
		}
		
		// create cypher by key
		CertifiedKey certifiedKey = new CertifiedKey(bytes);
		cypher = CypherFactory.get(ALGORITHM).create(certifiedKey);
		
		// the domain name for cookie
		domain = servletConfig.getInitParameter(SECURITY_DOMAIN_NAME);
		
		// get key for store user name in cookie
		userKey = servletConfig.getInitParameter(SECURITY_USER_NAME);
		if (StringUtil.isBlank(userKey)) {
			userKey = "corona.user";
		}

		// get key for store role names in cookie
		roleKey = servletConfig.getInitParameter(SECURITY_ROLE_NAME);
		if (StringUtil.isBlank(roleKey)) {
			roleKey = "corona.role";
		}
	}
	
	/**
	 * @param request the request
	 * @param name the cookie name
	 * @return the value
	 */
	private static Cookie getCookie(final HttpServletRequest request, final String name) {
		
		// find the cookie with specified name
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (name.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		
		// does not find the cookie with specified name
		return null;
	}

	/**
	 * @param request the request
	 * @param name the cookie name
	 * @return the value
	 * @throws Exception if fail to decrypt cookie name or value
	 */
	private static String getValue(final HttpServletRequest request, final String name) throws Exception {
		
		// find the cookie with specified name
		Cookie cookie = getCookie(request, name);
		if (cookie != null) {
			byte[] source = Base64.decode(cookie.getValue());
			byte[] target = cypher.decrypt(source);
				
			return new String(target);
		}
		
		// does not find the cookie with specified name
		return null;
	}

	/**
	 * @param request the HTTP SERVLET request
	 * @return the user stored in cookie
	 * @throws Exception if fail to decrypt cookie name or value
	 */
	static User load(final HttpServletRequest request) throws Exception {
		
		// find user name in cookie, if not find, just return
		String name = getValue(request, userKey);
		if (name == null) {
			return null;
		}
		
		// if have user, load it with its roles
		User user = new User(name);
		
		String roles = getValue(request, roleKey);
		if (!StringUtil.isBlank(roles)) {
			for (String role : roles.split(",")) {
				user.getRoles().add(new Role(role));
			}
		}
		
		return user;
	}
	
	/**
	 * @param request the HTTP SERVLET request
	 * @param response the HTTP SERVLET response
	 */
	static void delete(final HttpServletRequest request, final HttpServletResponse response) {

		// get cookie path from SERVLET context path
		String path = request.getSession().getServletContext().getContextPath();

		// delete user name cookie
		Cookie cookie = new Cookie(userKey, "");
		cookie.setMaxAge(-1); 
		cookie.setPath(path); 
		if (domain != null) {
			cookie.setDomain(domain);
		}
		
		response.addCookie(cookie); 
		
		// delete roles name cookie
		cookie = new Cookie(roleKey, "");
		cookie.setMaxAge(-1); 
		cookie.setPath(path); 
		if (domain != null) {
			cookie.setDomain(domain);
		}
		
		response.addCookie(cookie); 
	}
	
	/**
	 * @param request the HTTP SERVLET request
	 * @param response the HTTP SERVLET response
	 * @param user the user
	 * @throws CypherException if fail to encrypt cookie value
	 */
	static void save(
			final HttpServletRequest request, final HttpServletResponse response, final User user
	) throws CypherException {
		
		// get cookie path from SERVLET context path
		String path = request.getSession().getServletContext().getContextPath();
		
		// save user name
		byte[] source = cypher.encrypt(user.getName().getBytes());
		String target = Base64.encodeBytes(source);
		
		Cookie cookie = new Cookie(userKey, target); 
		cookie.setMaxAge(-1); 
		cookie.setPath(path); 
		if (domain != null) {
			cookie.setDomain(domain);
		}
		
		response.addCookie(cookie); 
		
		// save role
		if (user.getRoles().size() > 0) {
			
			String roles = user.getRoles().get(0).getName();
			for (int i = 1, count = user.getRoles().size(); i < count; i++) {
				roles = roles + "," + user.getRoles().get(i).getName();
			}
			
			source = cypher.encrypt(roles.getBytes());
			target = Base64.encodeBytes(source);

			cookie = new Cookie(roleKey, target); 
			cookie.setMaxAge(-1); 
			cookie.setPath(path); 
			if (domain != null) {
				cookie.setDomain(domain);
			}
			
			response.addCookie(cookie); 
		}
	}
}
