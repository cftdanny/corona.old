/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.handling.producer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.servlet.HandleException;
import com.corona.servlet.Restrictor;

/**
 * <p>All selectors for URL to be matched </p>
 *
 * @author $Author$
 * @version $Id$
 */
class Restrictors {

	/**
	 * all restrictors
	 */
	private List<Restrictor> restrictors;

	/**
	 * @param path the request path
	 * @param request the HTTP SERVLET request
	 * @param response the HTTP SERVLET response
	 * @return whether can access resource for request path
	 * @throws HandleException if fail to check accessing permission
	 */
	public boolean restrict(
			final String path, final HttpServletRequest request, final HttpServletResponse response
	) throws HandleException {
		
		if (this.restrictors != null) {
			
			for (Restrictor restrictor : this.restrictors) {
				if (restrictor.restrict(path, request, response)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param restrictor the new restrictor to be added
	 */
	public void add(final Restrictor restrictor) {
		
		if (this.restrictors == null) {
			this.restrictors = new ArrayList<Restrictor>();
		}
		this.restrictors.add(restrictor);
	}
}
