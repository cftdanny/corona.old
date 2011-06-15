/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.util.ArrayList;
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
	 * the children tokens
	 */
	private List<Token> tokens = new ArrayList<Token>();
	
	/**
	 * the array index
	 */
	private int index;

	/**
	 * @param name the field name
	 */
	ArrayToken(final String name) {
		this.name = name;
	}

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
	 * @param childTokenIndex the token index
	 * @return the child token
	 */
	public Token get(final int childTokenIndex) {
		
		if ((childTokenIndex >= 0) && (childTokenIndex < this.tokens.size())) {
			return this.tokens.get(index);
		} else {
			return null;
		}
	}
	
	/**
	 * @param childTokenIndex the token index
	 * @param childToken the child token
	 */
	public void set(final int childTokenIndex, final Token childToken) {
		
		for (int i = this.tokens.size(); i <= childTokenIndex; i++) {
			this.tokens.add(null);
		}
		
		this.tokens.remove(childTokenIndex);
		this.tokens.add(childTokenIndex, childToken);
	}
	
	/**
	 * @return the array index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param runner the token runner
	 * @param parent parent object node
	 */
	private void addValueToObjectNode(final TokenRunner runner, final ObjectNode parent) {
		
		ArrayNode record = (ArrayNode) parent.get(this.name);
		if (record == null) {
			record = runner.getMapper().createArrayNode();
			parent.put(this.name, record);
		}
		
		record.insert(this.index, runner.getValue());
	}

	/**
	 * @param runner the token runner
	 * @param parent parent object node
	 */
	private void addValueToArrayNode(final TokenRunner runner, final ArrayNode parent) {
		
		ObjectNode object = (ObjectNode) parent.get(runner.getIndex());
		if (object == null) {
			object = runner.getMapper().createObjectNode();
			parent.insert(runner.getIndex(), object);
		}
	
		ArrayNode record = (ArrayNode) object.get(this.name);
		if (record == null) {
			record = runner.getMapper().createArrayNode();
			object.put(this.name, record);
		}
		
		record.insert(this.index, runner.getValue());
	}

	/**
	 * @param runner the token runner
	 * @param parent parent object node
	 * @return object node for this token
	 */
	private ArrayNode addArrayToObjectNode(final TokenRunner runner, final ObjectNode parent) {
		
		ArrayNode node = (ArrayNode) parent.get(this.name);
		if (node == null) {
			node = runner.getMapper().createArrayNode();
			parent.put(this.name, node);
		}
		return node;
	}

	/**
	 * @param runner the token runner
	 * @param parent parent array node
	 * @return object node for this token
	 */
	private ArrayNode addArrayToArrayNode(final TokenRunner runner, final ArrayNode parent) {
		
		ObjectNode record = (ObjectNode) parent.get(runner.getIndex());
		if (record == null) {
			record = runner.getMapper().createObjectNode();
			parent.insert(runner.getIndex(), record);
		}
		
		ArrayNode node = (ArrayNode) record.get(this.name);
		if (node == null) {
			node = runner.getMapper().createArrayNode();
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
		
		ArrayNode node = null;
		if (!tokens.isEmpty()) {
			
			if (parent instanceof ObjectNode) {
				node = this.addArrayToObjectNode(runner, (ObjectNode) parent);
			} else {
				node = this.addArrayToArrayNode(runner, (ArrayNode) parent);
			}
		} else {
			
			if (parent instanceof ObjectNode) {
				this.addValueToObjectNode(runner, (ObjectNode) parent);
			} else {
				this.addValueToArrayNode(runner, (ArrayNode) parent); 
			}
		}
		runner.setIndex(this.index);

		return node;
	}
}
