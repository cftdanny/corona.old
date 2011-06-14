/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.util.List;

import org.codehaus.jackson.node.ObjectNode;

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
	 * @param expression the expression
	 * @param tokens the tokens that parsed according to expression
	 * @param current current object node
	 * @return the new object node
	 * @throws TokenParserException if fail to create object node
	 */
	ObjectNode create(
			TokenRunner runner, String expression, List<Token> tokens, ObjectNode current
	) throws TokenParserException;
}
