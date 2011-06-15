/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
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
	 * all children tokens
	 */
	private Map<String, Token> tokens = new HashMap<String, Token>();

	/**
	 * default constructor
	 */
	ObjectToken() {
		this(null);
	}

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
	 * @param childTokenName the child token name
	 * @return the child token
	 */
	public Token get(final String childTokenName) {
		return this.tokens.get(childTokenName);
	}
	
	/**
	 * @param childTokenName the child token name
	 * @param childToken the child token
	 */
	public void set(final String childTokenName, final Token childToken) {
		this.tokens.put(childTokenName, childToken);
	}
	
	/**
	 * @param runner the token runner
	 * @param parent parent object node
	 */
	private void addValueToObjectNode(final TokenRunner runner, final ObjectNode parent) {
		parent.put(this.name, runner.getValue());
	}
	
	/**
	 * @param runner the token runner
	 * @param parent parent array node
	 */
	private void addValueToArrayNode(final TokenRunner runner, final ArrayNode parent) {

		ObjectNode record = (ObjectNode) parent.get(runner.getIndex());
		if (record == null) {
			record = runner.getMapper().createObjectNode();
			parent.insert(runner.getIndex(), record);
		}
		record.put(this.name, runner.getValue());
	}
	
	/**
	 * @param runner the token runner
	 * @param parent parent object node
	 * @return object node for this token
	 */
	private ObjectNode addObjectToObjectNode(final TokenRunner runner, final ObjectNode parent) {
		
		ObjectNode node = (ObjectNode) parent.get(this.name);
		if (node == null) {
			node = runner.getMapper().createObjectNode();
			parent.put(this.name, node);
		}
		return node;
	}

	/**
	 * @param runner the token runner
	 * @param parent parent array node
	 * @return object node for this token
	 */
	private ObjectNode addObjectToArrayNode(final TokenRunner runner, final ArrayNode parent) {
		
		ObjectNode record = (ObjectNode) parent.get(runner.getIndex());
		if (record == null) {
			record = runner.getMapper().createObjectNode();
			parent.insert(runner.getIndex(), record);
		}
		
		ObjectNode node = (ObjectNode) record.get(this.name);
		if (node == null) {
			node = runner.getMapper().createObjectNode();
			record.put(this.name, node);
		}
		
		return node;
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
			
			if (parent instanceof ObjectNode) {
				node = this.addObjectToObjectNode(runner, (ObjectNode) parent);
			} else {
				node = this.addObjectToArrayNode(runner, (ArrayNode) parent);
			}
		} else {
			
			if (parent instanceof ObjectNode) {
				this.addValueToObjectNode(runner, (ObjectNode) parent);
			} else {
				this.addValueToArrayNode(runner, (ArrayNode) parent); 
			}
		}
		runner.setIndex(-1);
		
		return node;
	}
}
