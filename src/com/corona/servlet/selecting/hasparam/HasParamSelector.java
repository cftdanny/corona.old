/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.selecting.hasparam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.corona.servlet.Selector;
import com.corona.servlet.annotation.HasParam;

/**
 * <p>This Selector will check whether request exist parameters or not </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class HasParamSelector implements Selector {

	/**
	 * the parameter names
	 */
	private List<String> names;
	
	/**
	 * @param hasParam the annotation
	 */
	HasParamSelector(final HasParam hasParam) {
		
		this.names = new ArrayList<String>();
		for (String name : hasParam.value()) {
			this.names.add(name);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Selector#select(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public boolean select(final String path, final HttpServletRequest request) {
		
		Map<?, ?> parameters = request.getParameterMap();
		for (String name : this.names) {
			if (!parameters.containsKey(name)) {
				return false;
			}
		}
		return true;
	}
}
