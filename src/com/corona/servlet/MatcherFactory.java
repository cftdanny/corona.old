/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;

/**
 * <p>This factory is used to create {@link Matcher} with configuration by annotation in method. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the 
 */
public interface MatcherFactory<T extends Annotation> {

	/**
	 * @return the match annotation in method
	 */
	Class<T> getType();
	
	/**
	 * @param contextManagerFactory the current context manager factory
	 * @param method the method that is annotated with matcher annotation
	 * @param pattern the pattern of match annotation
	 * @return the matcher to match HTTP request URI
	 */
	Matcher create(ContextManagerFactory contextManagerFactory, Method method, T pattern);
}
