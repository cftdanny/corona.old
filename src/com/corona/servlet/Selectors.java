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
public class Selectors implements Selector {

	/**
	 * all selectors
	 */
	private List<Selector> selectors;

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Selector#select(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
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
