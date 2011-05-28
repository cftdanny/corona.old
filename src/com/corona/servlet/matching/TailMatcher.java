/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.matching;

import java.lang.reflect.Method;

import com.corona.servlet.AbstractMatcher;
import com.corona.servlet.MatchResult;

/**
 * <p>Match a request URI by head pattern </p>
 *
 * @author $Author$
 * @version $Id$
 */
class TailMatcher extends AbstractMatcher {

	/**
	 * the match priority
	 */
	private int priority;

	/**
	 * the head pattern
	 */
	private String pattern;
	
	/**
	 * the tail name
	 */
	private String name;
	
	/**
	 * @param method the method that is annotated with matcher annotation
	 * @param priority the match priority
	 * @param pattern the head pattern
	 * @param name the tail name
	 */
	TailMatcher(final Method method, final int priority, final String pattern, final String name) {
		super(method);
		
		this.priority = priority;
		this.pattern = pattern;
		this.name = name;
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
		
		// test whether path starts with head pattern
		if (!path.endsWith(this.pattern)) {
			return null;
		}
		
		// matched, and return matched result and matched parameters
		MatchResult result = new MatchResult(path);
		if (path.length() == this.pattern.length()) {
			result.set(this.name, "");
		} else {
			result.set(this.name, path.substring(0, path.length() - this.pattern.length()));
		}
		return result;
	}
}
