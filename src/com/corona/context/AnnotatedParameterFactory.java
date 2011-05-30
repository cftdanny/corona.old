/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.annotation.Annotation;


/**
 * <p>This factory is used to create {@link AnnotatedParameter} for an annotated parameter of in constructor 
 * or method. An {@link AnnotatedParameterFactory} is associated with an injection annotation in 
 * {@link ContextManagerFactory}, for example, {@link com.corona.context.spi.InjectAnnotatedParameterFactory} 
 * is associated with {@link com.corona.context.annotation.Inject}, and creates 
 * {@link com.corona.context.spi.InjectAnnotatedParameter} for a parameter that is annotated by 
 * {@link com.corona.context.annotation.Inject}.
 * </p>
 * 
 * <p>If implements parameter injection, it needs: </p>
 * <ol>
 * 	<li>Create an annotation, this annotation must annotated with {@link com.corona.context.annotation.InjectType}. 
 * 	</li>
 * 	<li>Create subclass {@link AnnotatedParameter}, it will get value from context manager by parameter annotation. 
 * 	</li>
 * 	<li>Create subclass {@link AnnotatedParameterFactory}, it will create {@link AnnotatedParameter} according to 
 * context manager factory and annotated parameter.
 * 	</li>
 * 	<li>Register new injection annotation and {@link AnnotatedParameterFactory} to context manager factory
 * 	in {@link Module} or {@link AbstractModule}. 
 * 	</li>
 * </ol>
 * 
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.AbstractModule
 * @see com.corona.context.AnnotatedParameter
 * @see com.corona.context.spi.ContextManagerFactoryImpl
 */
public interface AnnotatedParameterFactory {

	/**
	 * @param contextManagerFactory the context manager factory
	 * @param parameterType the parameter type
	 * @param annotations all annotations for parameter
	 * @return the new {@link AnnotatedParameter}
	 */
	AnnotatedParameter create(
			ContextManagerFactory contextManagerFactory, Class<?> parameterType, Annotation[] annotations
	);
}
