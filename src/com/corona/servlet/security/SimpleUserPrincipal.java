/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>The simple principal for logged in user name </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SimpleUserPrincipal implements Principal {

	/**
	 * the user name
	 */
	private String name;
	
	/**
	 * the roles
	 */
	private Set<String> roles = new HashSet<String>();
	
	/**
	 * @param user the user
	 */
	SimpleUserPrincipal(final User user) {
		
		this.name = user.getName();
		for (Role role : user.getRoles()) {
			roles.add(role.getName());
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @return the roles
	 */
	public Set<String> getRoles() {
		return roles;
	}
}
