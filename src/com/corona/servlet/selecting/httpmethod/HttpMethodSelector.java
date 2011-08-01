/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.selecting.httpmethod;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.corona.servlet.Selector;
import com.corona.servlet.annotation.HttpMethod;

/**
 * <p>The Selector to test whether allow to access content according to HTTP request method. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class HttpMethodSelector implements Selector {

	/**
	 * the supported HTTP methods
	 */
	private Set<String> methods = new HashSet<String>();
	
	/**
	 * @param method the annotation of HTTP method
	 */
	HttpMethodSelector(final HttpMethod method) {
		
		for (HttpMethod.Action httpmethod : method.value()) {
			
			switch (httpmethod) {
				
				case GET:
					methods.add("GET");
					break;
					
				case POST:
					methods.add("POST");
					break;
					
				case PUT:
					methods.add("PUT");
					break;
					
				case DELETE:
					methods.add("DELETE");
					break;

				default:
					break;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Selector#select(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public boolean select(final String path, final HttpServletRequest request) {
		return !this.methods.contains(request.getMethod());
	}
}
