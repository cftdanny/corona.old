/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.param;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

/**
 * <p>This token is used to represent an array token, for example, <b>products[1]</b>, <b>items[1].name</b>. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ArrayToken implements Token {

	/**
	 * the field name
	 */
	private String name;
	
	/**
	 * the children tokens
	 */
	private List<Token> tokens = new ArrayList<Token>();

	/**
	 * @param name the field name
	 */
	ArrayToken(final String name) {
		this.name = name;
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
			return this.tokens.get(childTokenIndex);
		} else {
			return null;
		}
	}
	
	/**
	 * @param childTokenIndex the token index
	 * @param childToken the child token
	 */
	public void set(final int childTokenIndex, final Token childToken) {
		
		if (childTokenIndex == this.tokens.size()) {
			this.tokens.add(childToken);
		} else if (childTokenIndex + 1 == this.tokens.size()) {
			
			this.tokens.remove(childTokenIndex);
			this.tokens.add(childToken);
		} else if (childTokenIndex < this.tokens.size()) {
			
			this.tokens.remove(childTokenIndex);
			this.tokens.add(childTokenIndex, childToken);
		} else {
			
			for (int i = this.tokens.size(); i < childTokenIndex; i++) {
				this.tokens.add(null);
			}
			this.tokens.add(childToken);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.injecting.param.Token#create(
	 * 	org.codehaus.jackson.map.ObjectMapper, org.codehaus.jackson.JsonNode
	 * )
	 */
	@Override
	public void create(final ObjectMapper mapper, final JsonNode parent) {
		
		ArrayNode parentNode = (ArrayNode) parent;
		for (int i = 0, count = this.tokens.size(); i < count; i++) {
			
			Token childToken = this.tokens.get(i);
			if (childToken != null) {
				
				if (childToken instanceof ValueToken) {
					
					ValueToken token = (ValueToken) childToken;
					parentNode.insert(i, token.getValue());
				} else if (childToken instanceof ObjectToken) {
					
					ObjectToken token = (ObjectToken) childToken;
					
					ObjectNode childNode = mapper.createObjectNode();
					parentNode.insert(i, childNode);
					
					token.create(mapper, childNode);
				} else {
					
					ArrayToken token = (ArrayToken) childToken;
					
					ArrayNode childNode = mapper.createArrayNode();
					parentNode.insert(i, childNode);
					
					token.create(mapper, childNode);
				}
			}
		}
	}
}
