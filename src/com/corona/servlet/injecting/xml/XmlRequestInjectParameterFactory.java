/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.xml;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

import com.corona.context.InjectParameter;
import com.corona.context.InjectParameterFactory;
import com.corona.context.ContextManagerFactory;

/**
 * <p>This factory is used to create {@link XmlRequestInjectParameter} for a parameter that is annotated with 
 * {@link JsonRequest}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class XmlRequestInjectParameterFactory implements InjectParameterFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectParameterFactory#create(
	 * 	com.corona.context.ContextManagerFactory, 
	 * java.lang.reflect.AccessibleObject, java.lang.Class, java.lang.annotation.Annotation[]
	 * )
	 */
	@Override
	public InjectParameter create(
			final ContextManagerFactory contextManagerFactory, 
			final AccessibleObject accessible, final Class<?> parameterType, final Annotation[] annotations
	) {
		return new XmlRequestInjectParameter(accessible, parameterType, annotations);
	}
}
