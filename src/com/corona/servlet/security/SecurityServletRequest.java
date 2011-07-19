/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <p>The HTTP SERVLET request that load user information from HTTP cookie </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SecurityServletRequest extends HttpServletRequestWrapper {

	/**
	 * the principal
	 */
	private SimpleUserPrincipal userPrincipal;
	
	/**
	 * @param request the HTTP SERVLET request
	 * @param user the logged user
	 */
	public SecurityServletRequest(final HttpServletRequest request, final User user) {
		super(request);
		
		this.userPrincipal = new SimpleUserPrincipal(user);
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.http.HttpServletRequestWrapper#getRemoteUser()
	 */
	@Override
	public String getRemoteUser() {
		return this.userPrincipal == null ? super.getRemoteUser() : this.userPrincipal.getName();
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.http.HttpServletRequestWrapper#getUserPrincipal()
	 */
	@Override
	public Principal getUserPrincipal() {
		return this.userPrincipal;
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.http.HttpServletRequestWrapper#isUserInRole(java.lang.String)
	 */
	@Override
	public boolean isUserInRole(final String role) {
		return this.userPrincipal.getRoles().contains(role);
	}
}
