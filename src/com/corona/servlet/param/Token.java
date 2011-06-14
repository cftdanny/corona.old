/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.util.List;

import org.codehaus.jackson.JsonNode;

/**
 * <p>Token is used to split parameter name or expression to part according to simple object and
 * array. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Token {

	/**
	 * @param runner the token runner
	 * @param tokens the tokens that parsed according to expression
	 * @param parent the parent node of this token
	 * @return the new object node
	 * @throws TokenParserException if fail to create object node
	 */
	JsonNode create(TokenRunner runner, List<Token> tokens, JsonNode parent) throws TokenParserException;
}
