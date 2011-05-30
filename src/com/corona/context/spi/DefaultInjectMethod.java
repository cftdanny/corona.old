/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.reflect.Method;

import com.corona.context.AbstractInjectMethod;
import com.corona.context.ContextManagerFactory;

/**
 * <p>This class is used to register a method that is annotated with injection annotation. 
 * Its arguments will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DefaultInjectMethod extends AbstractInjectMethod {

	/**
	 * @param contextManagerFactory the context manager factory
	 * @param annotatedMethod the method that is annotated with {@link Inject}
	 */
	DefaultInjectMethod(final ContextManagerFactory contextManagerFactory, final Method annotatedMethod) {
		super(contextManagerFactory, annotatedMethod);
	}
}
