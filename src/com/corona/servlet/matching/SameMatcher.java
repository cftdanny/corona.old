/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.matching;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.servlet.AbstractMatcher;
import com.corona.servlet.MatchResult;
import com.corona.servlet.annotation.Same;

/**
 * <p>This matcher will match request URI with pattern exactly same. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class SameMatcher extends AbstractMatcher {

	/**
	 * the match priority
	 */
	private int priority;

	/**
	 * the matching pattern
	 */
	private String[] patterns;
	
	/**
	 * @param contextManagerFactory the current context manager factory
	 * @param method the method that is annotated with matcher annotation
	 * @param same the same pattern
	 */
	SameMatcher(final ContextManagerFactory contextManagerFactory, final Method method, final Same same) {
		super(contextManagerFactory, method);

		this.priority = same.priority();
		this.patterns = same.value();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Matcher#getPriority()
	 */
	@Override
	public int getPriority() {
		return this.priority;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.AbstractMatcher#match(java.lang.String)
	 */
	@Override
	public MatchResult match(final String path) {
		
		for (String pattern : this.patterns) {
			
			if (pattern.equals(path)) {
				return new MatchResult(path);
			}
		}
		return null;
	}
}
