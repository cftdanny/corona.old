/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.matching;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.servlet.Matcher;
import com.corona.servlet.MatcherFactory;
import com.corona.servlet.annotation.Tail;

/**
 * <p>Create {@link HeadMatcher} with pattern in {@link Head}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TailMatcherFactory implements MatcherFactory<Tail> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.MatcherFactory#getType()
	 */
	@Override
	public Class<Tail> getType() {
		return Tail.class;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.MatcherFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method, java.lang.annotation.Annotation
	 * )
	 */
	@Override
	public Matcher create(final ContextManagerFactory contextManagerFactory, final Method method, final Tail tail) {
		return new TailMatcher(contextManagerFactory, method, tail);
	}
}
