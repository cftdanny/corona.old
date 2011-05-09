/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.reflect.Constructor;

import com.corona.context.ContextManagerFactory;
import com.corona.context.extension.DecoratedConstructor;
import com.corona.context.extension.DecoratedConstructorFactory;

/**
 * <p>This factory is used to create {@link InjectDecoratedConstructor} for a constructor that is 
 * annotated with {@link Inject}.  
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
class InjectDecoratedConstructorFactory implements DecoratedConstructorFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.extension.DecoratedConstructorFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Constructor
	 * )
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public DecoratedConstructor create(
			final ContextManagerFactory contextManagerFactory, final Constructor constructor
	) {
		return new InjectDecoratedConstructor(contextManagerFactory, constructor);
	}
}
