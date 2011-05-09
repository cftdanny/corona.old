/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.extension;

import java.lang.annotation.Annotation;

import com.corona.context.ContextManagerFactory;

/**
 * <p>This factory is used to create {@link DecoratedParameter} for an annotated parameter of in constructor 
 * or method. An {@link DecoratedParameterFactory} is associated with an injection annotation in 
 * {@link ContextManagerFactory}, for example, {@link com.corona.context.spi.InjectDecoratedParameterFactory} 
 * is associated with {@link com.corona.context.annotation.Inject}, and creates 
 * {@link com.corona.context.spi.InjectDecoratedParameter} for a parameter that is annotated by 
 * {@link com.corona.context.annotation.Inject}.
 * </p>
 * 
 * <p>If implements parameter injection, it needs: </p>
 * <ol>
 * 	<li>Create an annotation, this annotation must annotated with {@link com.corona.context.annotation.InjectType}. 
 * 	</li>
 * 	<li>Create subclass {@link DecoratedParameter}, it will get value from context manager by parameter annotation. 
 * 	</li>
 * 	<li>Create subclass {@link DecoratedParameterFactory}, it will create {@link DecoratedParameter} according to 
 * context manager factory and annotated parameter.
 * 	</li>
 * 	<li>Register new injection annotation and {@link DecoratedParameterFactory} to context manager factory
 * 	in {@link Module} or {@link AbstractModule}. 
 * 	</li>
 * </ol>
 * 
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.AbstractModule
 * @see com.corona.context.extension.DecoratedParameter
 * @see com.corona.context.spi.ContextManagerFactoryImpl
 */
public interface DecoratedParameterFactory {

	/**
	 * @param contextManagerFactory the context manager factory
	 * @param parameterType the parameter type
	 * @param annotations all annotations for parameter
	 * @return the new {@link DecoratedParameter}
	 */
	DecoratedParameter create(
			ContextManagerFactory contextManagerFactory, Class<?> parameterType, Annotation[] annotations
	);
}
