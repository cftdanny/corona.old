/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.matching;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.servlet.Matcher;
import com.corona.servlet.MatcherFactory;
import com.corona.servlet.annotation.Same;

/**
 * <p>Create {@link SameMatcher} with pattern in {@link Same}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SameMatcherFactory implements MatcherFactory<Same> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.MatcherFactory#getType()
	 */
	@Override
	public Class<Same> getType() {
		return Same.class;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.MatcherFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method, java.lang.annotation.Annotation
	 * )
	 */
	@Override
	public Matcher create(final ContextManagerFactory contextManagerFactory, final Method method, final Same same) {
		return new SameMatcher(contextManagerFactory, method, same);
	}
}
