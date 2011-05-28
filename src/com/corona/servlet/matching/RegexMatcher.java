/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.matching;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.corona.servlet.AbstractMatcher;
import com.corona.servlet.MatchResult;

/**
 * <p>Match a request URI by REGEX expression </p>
 *
 * @author $Author$
 * @version $Id$
 */
class RegexMatcher extends AbstractMatcher {

	/**
	 * the match priority
	 */
	private int priority;

	/**
	 * the head pattern
	 */
	private Pattern pattern;
	
	/**
	 * the prefix of group name
	 */
	private String name;
	
	/**
	 * @param method the method that is annotated with matcher annotation
	 * @param priority the match priority
	 * @param pattern the head pattern
	 * @param name the prefix of group name
	 */
	RegexMatcher(final Method method, final int priority, final String pattern, final String name) {
		super(method);
		
		this.priority = priority;
		this.pattern = Pattern.compile(pattern);
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
		
		// test whether request URI is matched with REGEX pattern
		Matcher matcher = this.pattern.matcher(path);
		if (!matcher.matches()) {
			return null;
		}
		
		// matched, and return matched result and matched parameters
		MatchResult result = new MatchResult(path);
		for (int i = 1; i <= matcher.groupCount(); i++) {
			result.set(this.name + Integer.toString(i), matcher.group(i));
		}
		return result;
	}
}
