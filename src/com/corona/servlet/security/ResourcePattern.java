/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

/**
 * <p>The resource with pattern to be checked its security </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ResourcePattern {

	/**
	 * @param path the HTTP request path
	 * @return whether path is matched with pattern
	 */
	boolean match(String path);
}
