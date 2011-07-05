/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.handling.component;

import javax.servlet.http.HttpServletRequest;

import com.corona.servlet.Handler;
import com.corona.servlet.MatchResult;
import com.corona.servlet.Matcher;
import com.corona.servlet.annotation.Controller;

/**
 * <p>This matcher is used to match request URI and children matcher. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ComponentMatcher implements Matcher {

	/**
	 * the matching priority
	 */
	private int priority;
	
	/**
	 * the base path
	 */
	private String basePath;
	
	/**
	 * the component handler
	 */
	private ComponentHandler componentHandler;
	
	/**
	 * @param componentHandler the component handler
	 * @param controller the controller that is annotated in component
	 */
	ComponentMatcher(final ComponentHandler componentHandler, final Controller controller) {
		this.componentHandler = componentHandler;
		this.priority = controller.priority();
		this.basePath = controller.value();
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
	 * @see com.corona.servlet.Matcher#match(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public MatchResult match(final String path, final HttpServletRequest request) {
		
		// test whether request path will be handled by children handler (component handler) or not
		if (path.startsWith(this.basePath)) {
			
			String childPath = path.substring(this.basePath.length());
			for (Handler handler : this.componentHandler.getChildren()) {
				
				// match child path with children handler, if match, will return this child handler
				MatchResult result = handler.getMatcher().match(childPath, request);
				if (result != null) {
					// restore matched URI and store matched handler
					result.setPath(path);
					result.set(ComponentHandler.class.getName(), handler);
					return result;
				}
			}
		} 
		
		// this request path doesn't handle by this component handler
		return null;
	}
}
