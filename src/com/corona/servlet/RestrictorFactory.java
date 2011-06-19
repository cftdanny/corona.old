/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;

/**
 * <p>This factory is used to create Restirctor by context manager factory and Restrict annotation </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the restrict annotation
 */
public interface RestrictorFactory<T extends Annotation> {

	/**
	 * @param contextManagerFactory the current context manager factory
	 * @param method the method that is annotated with matcher annotation
	 * @param restrict the restriction pattern
	 * @return the restrictor to check whether access resource or not
	 */
	Restrictor create(ContextManagerFactory contextManagerFactory, Method method, T restrict);
}
