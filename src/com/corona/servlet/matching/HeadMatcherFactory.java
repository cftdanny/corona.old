/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.matching;

import java.lang.reflect.Method;

import com.corona.servlet.Matcher;
import com.corona.servlet.MatcherFactory;
import com.corona.servlet.annotation.Head;

/**
 * <p>Create {@link HeadMatcher} with pattern in {@link Head}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class HeadMatcherFactory implements MatcherFactory<Head> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.MatcherFactory#getType()
	 */
	@Override
	public Class<Head> getType() {
		return Head.class;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.MatcherFactory#create(java.lang.reflect.Method, java.lang.annotation.Annotation)
	 */
	@Override
	public Matcher create(final Method method, final Head pattern) {
		return new HeadMatcher(method, pattern.value(), pattern.name());
	}
}
