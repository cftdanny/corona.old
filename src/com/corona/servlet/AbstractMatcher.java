/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.corona.servlet.annotation.GET;
import com.corona.servlet.annotation.POST;
import com.corona.servlet.annotation.PUT;
import com.corona.servlet.annotation.DELETE;

/**
 * <p>The helper {@link Matcher} that checks GET, POST, PUT and DELETE for HTTP request. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractMatcher implements Matcher {

	/**
	 * the matched HTTP request method
	 */
	private Set<String> methods = new HashSet<String>();
	
	/**
	 * @param method the method
	 */
	public AbstractMatcher(final Method method) {
		
		if (method.isAnnotationPresent(GET.class)) {
			this.methods.add("GET");
		}
		if (method.isAnnotationPresent(POST.class)) {
			this.methods.add("POST");
		}
		if (method.isAnnotationPresent(PUT.class)) {
			this.methods.add("PUT");
		}
		if (method.isAnnotationPresent(DELETE.class)) {
			this.methods.add("DELETE");
		}
		
		if ((this.methods.size() == 0) || (this.methods.size() == 4)) {
			this.methods = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Matcher#match(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public MatchResult match(final String path, final HttpServletRequest request) {
		
		if ((this.methods == null) || this.methods.contains(request.getMethod())) {
			return this.match(path);
		} else {
			return null;
		}
	}
	
	/**
	 * @param path the HTTP request path 
	 * @return the matched result or <code>null</code> if does not matched
	 */
	protected abstract MatchResult match(final String path);
}
