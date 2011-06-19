/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.matching;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.servlet.Matcher;
import com.corona.servlet.MatcherFactory;
import com.corona.servlet.annotation.Path;

/**
 * <p>This factory is used to create REST style matcher </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class PathMatcherFactory implements MatcherFactory<Path> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.MatcherFactory#getType()
	 */
	@Override
	public Class<Path> getType() {
		return Path.class;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.MatcherFactory#create(
	 * 	com.corona.context.ContextManagerFactory, java.lang.reflect.Method, java.lang.annotation.Annotation
	 * )
	 */
	@Override
	public Matcher create(final ContextManagerFactory contextManagerFactory, final Method method, final Path pattern) {
		return new PathMatcher(contextManagerFactory, method, pattern);
	}
}
