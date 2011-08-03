/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>All selectors for URL to be matched </p>
 *
 * @author $Author$
 * @version $Id$
 */
class Selectors {

	/**
	 * all selectors
	 */
	private List<Selector> selectors;

	/**
	 * @param path the request path
	 * @param request the HTTP SERVLET request
	 * @return whether can access resource for request path
	 */
	public boolean select(final String path, final HttpServletRequest request) {
		
		if (this.selectors != null) {
			
			for (Selector selector : this.selectors) {
				if (selector.select(path, request)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param selector the new selector to be added
	 */
	public void add(final Selector selector) {
		
		if (this.selectors == null) {
			this.selectors = new ArrayList<Selector>();
		}
		this.selectors.add(selector);
	}
}
