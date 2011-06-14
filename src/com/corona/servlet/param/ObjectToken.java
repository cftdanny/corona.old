/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.util.List;
import java.util.Queue;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

/**
 * <p>This token is used to represent an object token. For example, <b>user</b>, <b>user.name</b> 
 * parameter name. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ObjectToken implements Token {

	/**
	 * the name
	 */
	private String name;

	/**
	 * @param name the object name
	 */
	ObjectToken(final String name) {
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
		
		ObjectNode node = null;
		if (tokens.isEmpty()) {
			current.put(this.name, runner.getRequest().getParameter(expression));
		} else {
			node = runner.getMapper().createObjectNode();
			current.put(this.name, node);
		}
		return node;
	}
}
