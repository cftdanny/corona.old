/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.matching;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;
import com.corona.servlet.AbstractMatcher;
import com.corona.servlet.MatchResult;
import com.corona.servlet.annotation.Head;

/**
 * <p>Match a request URI by head pattern </p>
 *
 * @author $Author$
 * @version $Id$
 */
class HeadMatcher extends AbstractMatcher {

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
	 * @param contextManagerFactory the current context manager factory
	 * @param method the method that is annotated with matcher annotation
	 * @param head the head pattern
	 */
	HeadMatcher(final ContextManagerFactory contextManagerFactory, final Method method, final Head head) {
		super(contextManagerFactory, method);
		
		this.priority = head.priority();
		this.pattern = head.value();
		this.name = head.name();
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
		if (!pattern.startsWith(this.pattern)) {
			return null;
		}
		
		// matched, and return matched result and matched parameters
		MatchResult result = new MatchResult(path);
		result.set(this.name, path.substring(this.pattern.length()));
		return result;
	}
}
