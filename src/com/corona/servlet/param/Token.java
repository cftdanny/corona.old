/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import org.codehaus.jackson.map.ObjectMapper;
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
	 * @param mapper the object mapper
	 * @param parent the parent token descriptor
	 */
	void create(ObjectMapper mapper, ObjectNode parent);
}
