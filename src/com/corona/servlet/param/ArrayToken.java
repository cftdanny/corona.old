/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.util.List;

import org.codehaus.jackson.node.ObjectNode;

/**
 * <p>This token is used to represent an array token, for example, <b>products[1]</b>, <b>items[1].name</b>. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ArrayToken implements Token {

	/**
	 * the field name
	 */
	private String name;
	
	/**
	 * the array index
	 */
	private int index;
	
	/**
	 * @param name the field name
	 * @param index the array index
	 */
	ArrayToken(final String name, final int index) {
		this.name = name;
		this.index = index;
	}
	
	/**
	 * @return the field name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the array index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.test.json.Token#create(
	 * 	com.corona.test.json.TokenRunner, java.lang.String, java.util.List, org.codehaus.jackson.node.ObjectNode
	 * )
	 */
	@Override
	public ObjectNode create(
			final TokenRunner runner, final String expression, final List<Token> tokens, final ObjectNode current) {
		return null;
	}
}
