/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.util.List;

import org.codehaus.jackson.JsonNode;

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
	 * @see com.corona.servlet.param.Token#create(
	 * 	com.corona.servlet.param.TokenRunner, java.util.List, org.codehaus.jackson.JsonNode
	 * )
	 */
	@Override
	public JsonNode create(
			final TokenRunner runner, final List<Token> tokens, final JsonNode parent) throws TokenParserException {
		
		JsonNode node = null;
		if (!tokens.isEmpty()) {
			
			node = parent.findValue(this.name);
			if (node == null) {
				node = runner.getMapper().createObjectNode();
				runner.set(parent, this.name, node);
			}
			return node;
		} else {
			
			runner.set(parent, this.name, runner.getValue());
		}
		runner.setIndex(-1);
		
		return node;
	}
}
