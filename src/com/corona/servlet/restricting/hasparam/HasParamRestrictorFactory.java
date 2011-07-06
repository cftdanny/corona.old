/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.restricting.hasparam;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.servlet.Restrictor;
import com.corona.servlet.RestrictorFactory;
import com.corona.servlet.annotation.HasParam;

/**
 * <p>This factory is used to create {@link HasParam} by annotation {@link HasParam} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class HasParamRestrictorFactory implements RestrictorFactory<HasParam> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.RestrictorFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method, java.lang.annotation.Annotation
	 * )
	 */
	@Override
	public Restrictor create(
			final ContextManagerFactory contextManagerFactory, final Method method, final HasParam restrict) {
		return null;
	}
}
