/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.restricting.securedrequest;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.servlet.Restrictor;
import com.corona.servlet.RestrictorFactory;
import com.corona.servlet.annotation.SecuredRequest;

/**
 * <p>This factory is used to create {@LoggedInRestrictor} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SecuredRequestRestrictorFactory implements RestrictorFactory<SecuredRequest> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.RestrictorFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method, java.lang.annotation.Annotation
	 * )
	 */
	@Override
	public Restrictor create(
			final ContextManagerFactory contextManagerFactory, final Method method, final SecuredRequest securedRequest
	) {
		
		return new SecuredRequestRestrictor();
	}
}
