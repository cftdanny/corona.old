/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.restricting.allowroles;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.servlet.Restrictor;
import com.corona.servlet.RestrictorFactory;
import com.corona.servlet.annotation.AllowRoles;

/**
 * <p>This factory is used to create {@AllowRolesRestrictor} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AllowRolesRestrictorFactory implements RestrictorFactory<AllowRoles> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.RestrictorFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method, java.lang.annotation.Annotation
	 * )
	 */
	@Override
	public Restrictor create(
			final ContextManagerFactory contextManagerFactory, final Method method, final AllowRoles allowRoles) {
		
		return new AllowRolesRestrictor(allowRoles);
	}
}
