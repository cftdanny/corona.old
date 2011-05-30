/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.context.InjectMethod;
import com.corona.context.InjectMethodFactory;

/**
 * <p>This factory is used to create {@link InjectProperty} for a property that is annotated 
 * with {@link Inject}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DefaultInjectMethodFactory implements InjectMethodFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectMethodFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method
	 * )
	 */
	@Override
	public InjectMethod create(final ContextManagerFactory contextManagerFactory, final Method method) {
		return new DefaultInjectMethod((ContextManagerFactoryImpl) contextManagerFactory, method);
	}
}
