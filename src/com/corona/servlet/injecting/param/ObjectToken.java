/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.param;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
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
	 * {@inheritDoc}
	 * @see com.corona.servlet.injecting.param.Token#create(
	 * 	org.codehaus.jackson.map.ObjectMapper, org.codehaus.jackson.JsonNode
	 * )
	 */
	@Override
	public void create(final ObjectMapper mapper, final JsonNode parent) {
		
		ObjectNode parentNode = (ObjectNode) parent;
		for (Token childToken : this.tokens.values()) {
			
			if (childToken instanceof ValueToken) {
				
				ValueToken token = (ValueToken) childToken;
				parentNode.put(token.getName(), token.getValue());
			} else if (childToken instanceof ObjectToken) {
				
				ObjectToken token = (ObjectToken) childToken;
				ObjectNode childNode = mapper.createObjectNode();
				parentNode.put(token.getName(), childNode);
				
				token.create(mapper, childNode);
			} else {
				
				ArrayToken token = (ArrayToken) childToken;
				
				ArrayNode childNode = mapper.createArrayNode();
				parentNode.put(token.getName(), childNode);
				
				token.create(mapper, childNode);
			}
		}
	}
}
