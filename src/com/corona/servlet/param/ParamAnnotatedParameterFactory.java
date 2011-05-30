/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.lang.annotation.Annotation;

import com.corona.context.AnnotatedParameter;
import com.corona.context.AnnotatedParameterFactory;
import com.corona.context.ContextManagerFactory;

/**
 * <p>This factory is used to create {@link ParamAnnotatedParameter} for a parameter that is annotated with 
 * {@link Inject}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ParamAnnotatedParameterFactory implements AnnotatedParameterFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AnnotatedParameterFactory#create(
	 * com.corona.context.ContextManagerFactory, java.lang.Class, java.lang.annotation.Annotation[]
	 * )
	 */
	@Override
	public AnnotatedParameter create(
			final ContextManagerFactory contextManagerFactory, 
			final Class<?> parameterType, final Annotation[] annotations
	) {
		return new ParamAnnotatedParameter(parameterType, annotations);
	}
}
