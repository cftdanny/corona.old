/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>All restrictors for web resource path </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Restrictors implements Restrictor {

	/**
	 * all restrictors
	 */
	private List<Restrictor> restrictors;

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Restrictor#restrict(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public boolean restrict(final String path, final HttpServletRequest request) {
		
		if (this.restrictors != null) {
			
			for (Restrictor restrictor : this.restrictors) {
				if (restrictor.restrict(path, request)) {
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
