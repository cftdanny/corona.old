/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.matching;

import java.lang.reflect.Method;

import com.corona.servlet.AbstractMatcher;
import com.corona.servlet.MatchResult;

/**
 * <p>This matcher will match request URI with pattern exactly same. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class SameMatcher extends AbstractMatcher {

	/**
	 * the matching pattern
	 */
	private String pattern;
	
	/**
	 * @param method the method that is annotated with matcher annotation
	 * @param pattern the matching pattern
	 */
	SameMatcher(final Method method, final String pattern) {
		super(method);
		this.pattern = pattern;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.AbstractMatcher#match(java.lang.String)
	 */
	@Override
	public MatchResult match(final String path) {
		return (this.pattern.equals(path)) ? new MatchResult(path) : null;
	}
}
