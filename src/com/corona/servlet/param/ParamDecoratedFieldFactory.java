/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.lang.reflect.Field;

import com.corona.context.ContextManagerFactory;
import com.corona.context.extension.DecoratedFieldFactory;
import com.corona.context.extension.DecoratedField;

/**
 * <p>This factory is used to create {@link ParamDecoratedField} for a field that is annotated with 
 * {@link Inject}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ParamDecoratedFieldFactory implements DecoratedFieldFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.extension.DecoratedFieldFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Field
	 * )
	 */
	@Override
	public DecoratedField create(final ContextManagerFactory contextManagerFactory, final Field field) {
		return new ParamDecoratedField(field);
	}
}
