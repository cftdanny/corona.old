/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.context.extension.DecoratedProperty;
import com.corona.context.extension.DecoratedPropertyFactory;

/**
 * <p>This factory is used to create {@link DecoratedProperty} for a property that is annotated 
 * with {@link Inject}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class InjectDecoratedPropertyFactory implements DecoratedPropertyFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.extension.DecoratedPropertyFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method
	 * )
	 */
	@Override
	public DecoratedProperty create(final ContextManagerFactory contextManagerFactory, final Method property) {
		return new InjectDecoratedProperty(contextManagerFactory, property);
	}
}
