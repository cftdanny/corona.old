/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.reflect.Constructor;

import com.corona.context.InjectConstructor;
import com.corona.context.InjectConstructorFactory;
import com.corona.context.ContextManagerFactory;

/**
 * <p>This factory is used to create {@link DefaultInjectConstructor} for a constructor that is 
 * annotated with {@link Inject}.  
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
class DefaultInjectConstructorFactory implements InjectConstructorFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectConstructorFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Constructor
	 * )
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public InjectConstructor create(
			final ContextManagerFactory contextManagerFactory, final Constructor constructor
	) {
		return new DefaultInjectConstructor(contextManagerFactory, constructor);
	}
}
