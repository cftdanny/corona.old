/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.lang.annotation.Annotation;

import com.corona.context.ContextManagerFactory;
import com.corona.context.extension.DecoratedParameter;
import com.corona.context.extension.DecoratedParameterFactory;

/**
 * <p>This factory is used to create {@link InjectDecoratedParameter} for a parameter that is annotated with 
 * {@link Inject}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class InjectDecoratedParameterFactory implements DecoratedParameterFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.extension.DecoratedParameterFactory#create(
	 * com.corona.context.ContextManagerFactory, java.lang.Class, java.lang.annotation.Annotation[]
	 * )
	 */
	@Override
	public DecoratedParameter create(
			final ContextManagerFactory contextManagerFactory, 
			final Class<?> parameterType, final Annotation[] annotations
	) {
		return new InjectDecoratedParameter(parameterType, annotations);
	}
}
