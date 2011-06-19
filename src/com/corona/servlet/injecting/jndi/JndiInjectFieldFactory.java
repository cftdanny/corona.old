/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.jndi;

import java.lang.reflect.Field;

import com.corona.context.InjectField;
import com.corona.context.InjectFieldFactory;
import com.corona.context.ContextManagerFactory;

/**
 * <p>This factory is used to create {@link JndiInjectField} for a field that is annotated with 
 * {@link Inject}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class JndiInjectFieldFactory implements InjectFieldFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectFieldFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Field
	 * )
	 */
	@Override
	public InjectField create(final ContextManagerFactory contextManagerFactory, final Field field) {
		return new JndiInjectField(field);
	}
}
