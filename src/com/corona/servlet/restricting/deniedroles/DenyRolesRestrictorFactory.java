/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.restricting.deniedroles;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.servlet.Restrictor;
import com.corona.servlet.RestrictorFactory;
import com.corona.servlet.annotation.DenyRoles;

/**
 * <p>This factory is used to create {@DenyRolesRestrictor} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DenyRolesRestrictorFactory implements RestrictorFactory<DenyRoles> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.RestrictorFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method, java.lang.annotation.Annotation
	 * )
	 */
	@Override
	public Restrictor create(
			final ContextManagerFactory contextManagerFactory, final Method method, final DenyRoles denyRoles) {
		
		return new DenyRolesRestrictor(denyRoles);
	}
}
