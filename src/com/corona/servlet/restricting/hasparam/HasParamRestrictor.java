/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.restricting.hasparam;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.corona.servlet.Restrictor;
import com.corona.servlet.annotation.HasParam;

/**
 * <p>This restrictor will check whether request exist parameters or not </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class HasParamRestrictor implements Restrictor {

	/**
	 * the parameter names
	 */
	private List<String> names;
	
	/**
	 * @param hasParam the annotation
	 */
	HasParamRestrictor(final HasParam hasParam) {
		
		this.names = new ArrayList<String>();
		for (String name : hasParam.value()) {
			this.names.add(name);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Restrictor#restrict(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public boolean restrict(final String path, final HttpServletRequest request) {
		
		for (String name : this.names) {
			if (request.getParameter(name) == null) {
				return false;
			}
		}
		
		return true;
	}
}
