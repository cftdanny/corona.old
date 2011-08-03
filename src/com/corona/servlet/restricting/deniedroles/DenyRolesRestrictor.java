/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.restricting.deniedroles;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.servlet.HandleException;
import com.corona.servlet.Restrictor;
import com.corona.servlet.annotation.DenyRoles;

/**
 * <p>Don't allow user with specified roles to access resource </p>
 *
 * @author $Author$
 * @version $Id$
 */
class DenyRolesRestrictor implements Restrictor {

	/**
	 * all roles don't allow to access resource
	 */
	private Set<String> roles = new HashSet<String>();
	
	/**
	 * @param denyRoles all roles don't allow to access resource
	 */
	DenyRolesRestrictor(final DenyRoles denyRoles) {
		
		for (String role : denyRoles.value()) {
			this.roles.add(role);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Restrictor#restrict(
	 * 	java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse
	 * )
	 */
	@Override
	public boolean restrict(
			final String path, final HttpServletRequest request, final HttpServletResponse response
	) throws HandleException {
		
		Principal principal = request.getUserPrincipal();
		
		// if user isn't logged in, request to logged in
		if (principal == null) {
			
			try {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} catch (IOException e) {
				throw new HandleException("Fail send UNAUTHORIZED error command to client", e);
			}
			return true;
		} 
		
		// test whether user has specified roles
		for (String role : this.roles) {
			
			if (request.isUserInRole(role)) {
				try {
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				} catch (IOException e) {
					throw new HandleException("Fail send FORBIDDEN error command to client", e);
				}
				return true;
			}
		}
		return false;
	}
}
