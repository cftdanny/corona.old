/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.param;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * <p>The token for value </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ValueToken implements Token {

	/**
	 * the token name
	 */
	private String name;
	
	/**
	 * the token value
	 */
	private String value;
	
	/**
	 * @param value the token value
	 */
	ValueToken(final String value) {
		this(null, value);
	}

	/**
	 * @param name the token name
	 * @param value the token value
	 */
	ValueToken(final String name, final String value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * @return the token name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the token value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.injecting.param.Token#create(
	 * 	org.codehaus.jackson.map.ObjectMapper, org.codehaus.jackson.JsonNode
	 * )
	 */
	@Override
	public void create(final ObjectMapper mapper, final JsonNode parent) {
	}
}
