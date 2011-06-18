/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.cookieparam;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.context.InjectProperty;
import com.corona.context.InjectPropertyFactory;

/**
 * <p>This factory is used to create {@link InjectProperty} for a property that is annotated 
 * with {@link Inject}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class CookieParamInjectPropertyFactory implements InjectPropertyFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectPropertyFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method
	 * )
	 */
	@Override
	public InjectProperty create(final ContextManagerFactory contextManagerFactory, final Method property) {
		return new CookieParamInjectProperty(contextManagerFactory, property);
	}
}
