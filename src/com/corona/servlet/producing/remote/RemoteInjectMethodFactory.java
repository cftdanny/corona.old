/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.context.InjectMethod;
import com.corona.context.InjectMethodFactory;

/**
 * <p>This factory is used to create {@link RemoteInjectMethod} for a property that is annotated 
 * with {@link Remote}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class RemoteInjectMethodFactory implements InjectMethodFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectMethodFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method
	 * )
	 */
	@Override
	public InjectMethod create(final ContextManagerFactory contextManagerFactory, final Method method) {
		return new RemoteInjectMethod(contextManagerFactory, method);
	}
}
