/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.handling.resource;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.corona.servlet.MatchResult;
import com.corona.servlet.Matcher;

/**
 * <p>This matcher is used to match request by URI head defined in {@link ResourceHandler}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ResourceMatcher implements Matcher {

	/**
	 * the resource handler
	 */
	private ResourceHandler handler;
	
	/**
	 * @param handler the resource handler
	 */
	ResourceMatcher(final ResourceHandler handler) {
		this.handler = handler;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Matcher#getPriority()
	 */
	@Override
	public int getPriority() {
		return this.handler.getPriority();
	}

	/**
	 * @param context the SERVLET context
	 * @param path the request path
	 * @return whether resource with same name as path exists or not
	 */
	private boolean exists(final ServletContext context, final String path) {
		
		try {
			return context.getResource(path) != null;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Matcher#match(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public MatchResult match(final String path, final HttpServletRequest request) {
		
		if ((this.handler.getHead() == null) || (path.startsWith(this.handler.getHead()))) {
			if (this.exists(request.getSession().getServletContext(), path)) {
				return new MatchResult(path);
			}
		}
		
		return null;
	}
}
