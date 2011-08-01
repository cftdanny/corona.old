/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.selecting.httpmethod;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.servlet.Selector;
import com.corona.servlet.SelectorFactory;
import com.corona.servlet.annotation.HttpMethod;

/**
 * <p>this factory is used to create {@link HttpMethodSelector} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class HttpMethodSelectorFactory implements SelectorFactory<HttpMethod> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.SelectorFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method, java.lang.annotation.Annotation
	 * )
	 */
	@Override
	public Selector create(
			final ContextManagerFactory contextManagerFactory, final Method method, final HttpMethod httpMethod
	) {
		return new HttpMethodSelector(httpMethod);
	}
}
