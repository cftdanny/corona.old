/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
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
	 * @see com.corona.servlet.param.Token#create(
	 * 	com.corona.servlet.param.TokenRunner, java.util.List, org.codehaus.jackson.JsonNode
	 * )
	 */
	@Override
	public JsonNode create(
			final TokenRunner runner, final List<Token> tokens, final JsonNode parent) throws TokenParserException {
		
		ArrayNode node = null;
		if (parent instanceof ObjectNode) {
			node = (ArrayNode) parent.findValue(this.name);
			if (node == null) {
				node = runner.getMapper().createArrayNode();
				runner.set(parent, this.name, node);
			}
		} else {
			node = (ArrayNode) ((ArrayNode) parent).get(this.index);
			if (node == null) {
				node = runner.getMapper().createArrayNode();
				runner.set(parent, this.name, node);
			}
		}
		runner.setIndex(this.index);
		
		if (tokens.isEmpty()) {
			runner.set(node, null, runner.getValue());
		}
		return node;
	}
}
