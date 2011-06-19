/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.matching;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.corona.context.ConfigurationException;
import com.corona.context.ContextManagerFactory;
import com.corona.servlet.AbstractMatcher;
import com.corona.servlet.MatchResult;
import com.corona.servlet.annotation.Path;

/**
 * <p>Match a request URI by path with or without REGEX expression style. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class PathMatcher extends AbstractMatcher {

	/**
	 * the match priority
	 */
	private int priority;

	/**
	 * the path pattern
	 */
	private Pattern pattern;
	
	/**
	 * the variable name and group pairs
	 */
	private Map<Integer, String> groups = new HashMap<Integer, String>();
	
	/**
	 * @param contextManagerFactory the current context manager factory
	 * @param method the method that is annotated with matcher annotation
	 * @param path the path pattern
	 */
	PathMatcher(final ContextManagerFactory contextManagerFactory, final Method method, final Path path) {
		super(contextManagerFactory, method);
		this.priority = path.priority();
		
		// the state of parsing machine
		int state = 0;
		// the group index after matched with request URI, used to find variable name
		int group = 0;
		
		// the buffer to store matcher's REGEX expression
		StringBuilder builder = new StringBuilder();
		
		// the variable name and its REGEX expression
		String name = "", value = "";
		
		// parse path pattern with state machine
		for (char c : path.value().toCharArray()) {
			
			switch (state) {
				case 0:
					if (c == '{') {
						name = "";
						value = "";
						state = 1;
					} else {
						builder.append(c);
					}
					break;
					
				case 1:
					if (c == '}') {
						builder.append("([^/]+)");
						groups.put(group, name);
						group = group + 1;
						
						state = 0;
					} else if (c == ':') {
						state = 2;
					} else {
						name = name + c;
					}
					break;
					
				default:
					if (c == '}') {
						if ("".equals(value)) {
							value = "[^/]+";
						}
						builder.append("(" + value + ")");
						groups.put(group, name);
						group = group + 1;
						
						state = 0;
					} else {
						value = value + c;
					}
					break;	
			}
		}
		
		if (state != 0) {
			throw new ConfigurationException("Path pattern [{0}] is invalid", pattern);
		}
		
		this.pattern = Pattern.compile(builder.toString());
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
		
		// match request URI with path pattern
		Matcher matcher = this.pattern.matcher(path);
		if ((!matcher.matches()) || (matcher.groupCount() != this.groups.size())) {
			return null;
		}
		
		// find matched value and store it with name, name is found by matched group
		MatchResult result = new MatchResult(path);
		for (int i = 0; i < matcher.groupCount(); i++) {
			
			String name = this.groups.get(i);
			String value = matcher.group(i + 1);
			
			result.set(name, value);
		}
		return result;
	}
}
