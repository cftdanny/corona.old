/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.param;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

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
	void create(ObjectMapper mapper, JsonNode parent);
}
