/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;

/**
 * <p>This factory is used to create Selector by context manager factory and Selector annotation </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the Selector annotation
 */
public interface SelectorFactory<T extends Annotation> {

	/**
	 * @param contextManagerFactory the current context manager factory
	 * @param method the method that is annotated with Selector annotation
	 * @param selectorType the selector annotation
	 * @return the selector to check whether access resource or not
	 */
	Selector create(ContextManagerFactory contextManagerFactory, Method method, T selectorType);
}
