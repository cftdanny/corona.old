/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.lang.reflect.Field;

import com.corona.context.AnnotatedField;
import com.corona.context.AnnotatedFieldFactory;
import com.corona.context.ContextManagerFactory;

/**
 * <p>This factory is used to create {@link ParamAnnotatedField} for a field that is annotated with 
 * {@link Inject}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ParamAnnotatedFieldFactory implements AnnotatedFieldFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AnnotatedFieldFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Field
	 * )
	 */
	@Override
	public AnnotatedField create(final ContextManagerFactory contextManagerFactory, final Field field) {
		return new ParamAnnotatedField(field);
	}
}
