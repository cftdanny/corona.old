/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.util.List;

/**
 * <p>The token for value </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ValueToken implements Token {

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
	 * @see com.corona.servlet.param.Token#create(com.corona.servlet.param.TokenRunner, java.util.List, com.corona.servlet.param.Token)
	 */
	@Override
	public void create(TokenRunner runner, List<TokenDescriptor> descriptors, Token parent) throws TokenParserException {
	}
}
