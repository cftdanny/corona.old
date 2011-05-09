/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.context.extension.DecoratedMethodFactory;
import com.corona.context.extension.DecoratedMethod;

/**
 * <p>This factory is used to create {@link DecoratedProperty} for a property that is annotated 
 * with {@link Inject}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class InjectDecoratedMethodFactory implements DecoratedMethodFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.extension.DecoratedMethodFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method
	 * )
	 */
	@Override
	public DecoratedMethod create(final ContextManagerFactory contextManagerFactory, final Method method) {
		return new InjectDecoratedMethod((ContextManagerFactoryImpl) contextManagerFactory, method);
	}
}
