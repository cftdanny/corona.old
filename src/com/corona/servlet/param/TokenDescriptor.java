/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.util.List;

/**
 * <p>The token descriptor </p>
 *
 * @author $Author$
 * @version $Id$
 */
interface TokenDescriptor {

	/**
	 * @param runner the token runner
	 * @param descriptors the token descriptors that parsed according to expression
	 * @param parent the parent token descriptor
	 * @return current token
	 * @throws TokenParserException if fail to create object node
	 */
	Token create(
			TokenRunner runner, List<TokenDescriptor> descriptors, Token parent) throws TokenParserException;
}
