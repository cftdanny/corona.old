/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.matching;

import java.lang.reflect.Method;

import com.corona.servlet.Matcher;
import com.corona.servlet.MatcherFactory;
import com.corona.servlet.annotation.Regex;

/**
 * <p>Create REGEX matcher by {@link Regex} matcher annotation </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class RegexMatcherFactory implements MatcherFactory<Regex> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.MatcherFactory#getType()
	 */
	@Override
	public Class<Regex> getType() {
		return Regex.class;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.MatcherFactory#create(java.lang.reflect.Method, java.lang.annotation.Annotation)
	 */
	@Override
	public Matcher create(final Method method, final Regex pattern) {
		return new RegexMatcher(method, pattern.value(), pattern.name());
	}
}
