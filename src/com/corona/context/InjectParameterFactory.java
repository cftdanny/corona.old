/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

/**
 * <p>This factory is used to create {@link InjectParameter} for an annotated parameter of in constructor 
 * or method. An {@link InjectParameterFactory} is associated with an injection annotation in 
 * {@link ContextManagerFactory}, for example, {@link com.corona.context.spi.DefaultInjectParameterFactory} 
 * is associated with {@link com.corona.context.annotation.Inject}, and creates 
 * {@link com.corona.context.spi.DefaultInjectParameter} for a parameter that is annotated by 
 * {@link com.corona.context.annotation.Inject}.
 * </p>
 * 
 * <p>If implements parameter injection, it needs: </p>
 * <ol>
 * 	<li>Create an annotation, this annotation must annotated with {@link com.corona.context.annotation.InjectType}. 
 * 	</li>
 * 	<li>Create subclass {@link InjectParameter}, it will get value from context manager by parameter annotation. 
 * 	</li>
 * 	<li>Create subclass {@link InjectParameterFactory}, it will create {@link InjectParameter} according to 
 * context manager factory and annotated parameter.
 * 	</li>
 * 	<li>Register new injection annotation and {@link InjectParameterFactory} to context manager factory
 * 	in {@link Module} or {@link AbstractModule}. 
 * 	</li>
 * </ol>
 * 
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.AbstractModule
 * @see com.corona.context.InjectParameter
 * @see com.corona.context.spi.ContextManagerFactoryImpl
 */
public interface InjectParameterFactory {

	/**
	 * @param contextManagerFactory the context manager factory
	 * @param accessible the constructor or method to inject parameter
	 * @param parameterType the parameter type
	 * @param annotations all annotations for parameter
	 * @return the new {@link InjectParameter}
	 */
	InjectParameter create(
			ContextManagerFactory contextManagerFactory, 
			AccessibleObject accessible, Class<?> parameterType, Annotation[] annotations
	);
}
