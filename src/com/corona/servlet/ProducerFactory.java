/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.lang.annotation.Annotation;

import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;

/**
 * <p>This factory is used to create {@link Producer} by an annotation type. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the annotation type
 */
public interface ProducerFactory<T extends Annotation> {

	/**
	 * <p>Create producer that is used to create HTTP response by an outcome from method. The arguments of
	 * method will be resolved from current context manager.
	 * </p>
	 * 
	 * @param key the component key
	 * @param method the method that produce HTTP SERVLET response
	 * @return the producer
	 */
	Producer create(Key<?> key, DecoratedMethod method);
}
