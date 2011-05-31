/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.reflect.Constructor;

import com.corona.context.AbstractInjectConstructor;
import com.corona.context.ContextManagerFactory;

/**
 * <p>This class is used to create new component instance by constructor annotated with {@link Inject}. The
 * parameters value of constructor will be resolved from current context manager. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class DefaultInjectConstructor extends AbstractInjectConstructor {

	/**
	 * @param contextManagerFactory the current context manager factory
	 * @param constructor the constructor
	 */
	DefaultInjectConstructor(final ContextManagerFactory contextManagerFactory, final Constructor<?> constructor) {
		super(contextManagerFactory, constructor);
	}
}
