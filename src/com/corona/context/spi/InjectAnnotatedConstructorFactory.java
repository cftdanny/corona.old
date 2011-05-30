/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.reflect.Constructor;

import com.corona.context.AnnotatedConstructor;
import com.corona.context.AnnotatedConstructorFactory;
import com.corona.context.ContextManagerFactory;

/**
 * <p>This factory is used to create {@link InjectAnnotatedConstructor} for a constructor that is 
 * annotated with {@link Inject}.  
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
class InjectAnnotatedConstructorFactory implements AnnotatedConstructorFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AnnotatedConstructorFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Constructor
	 * )
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AnnotatedConstructor create(
			final ContextManagerFactory contextManagerFactory, final Constructor constructor
	) {
		return new InjectAnnotatedConstructor(contextManagerFactory, constructor);
	}
}
