/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.selecting.hasparam;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.servlet.Selector;
import com.corona.servlet.SelectorFactory;
import com.corona.servlet.annotation.HasParam;

/**
 * <p>This factory is used to create {@link HasParam} by annotation {@link HasParam} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class HasParamSelectorFactory implements SelectorFactory<HasParam> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.SelectorFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method, java.lang.annotation.Annotation
	 * )
	 */
	@Override
	public Selector create(
			final ContextManagerFactory contextManagerFactory, final Method method, final HasParam hasParam) {
		
		return new HasParamSelector(hasParam);
	}
}
