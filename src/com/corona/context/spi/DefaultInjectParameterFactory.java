/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.annotation.Annotation;

import com.corona.context.InjectParameter;
import com.corona.context.InjectParameterFactory;
import com.corona.context.ContextManagerFactory;

/**
 * <p>This factory is used to create {@link DefaultInjectParameter} for a parameter that is annotated with 
 * {@link Inject}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DefaultInjectParameterFactory implements InjectParameterFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectParameterFactory#create(
	 * com.corona.context.ContextManagerFactory, java.lang.Class, java.lang.annotation.Annotation[]
	 * )
	 */
	@Override
	public InjectParameter create(
			final ContextManagerFactory contextManagerFactory, 
			final Class<?> parameterType, final Annotation[] annotations
	) {
		return new DefaultInjectParameter(parameterType, annotations);
	}
}
