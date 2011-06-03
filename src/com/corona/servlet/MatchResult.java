/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>The matched result that matcher returns HTTP request URI. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class MatchResult implements Iterable<String> {

	/**
	 * the request URI matched
	 */
	private String path;
	
	/**
	 * the matched result
	 */
	private Map<String, Object> matches = new HashMap<String, Object>();

	/**
	 * @param path the request URI matched
	 */
	public MatchResult(final String path) {
		this.path = path;
	}
	
	/**
	 * @return the request URI matched
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the request URI matched
	 */
	public void setPath(final String path) {
		this.path = path;
	}
	
	/**
	 * <p>Whether matched result contains value with a name. </p>
	 * 
	 * @param name the name
	 * @return <code>true</code> if it contains value with name
	 */
	public boolean contains(final String name) {
		return this.matches.containsKey(name);
	}
	
	/**
	 * <p>Find the matched value by name. </p>
	 * 
	 * @param name the name
	 * @return the value of name or <code>null</code> if does not exist
	 */
	public Object get(final String name) {
		return matches.get(name);
	}
	
	/**
	 * <p>Add a new matched name and value. </p>
	 * 
	 * @param name the name
	 * @param value the value
	 */
	public void set(final String name, final Object value) {
		this.matches.put(name, value);
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<String> iterator() {
		return matches.keySet().iterator();
	}
}
