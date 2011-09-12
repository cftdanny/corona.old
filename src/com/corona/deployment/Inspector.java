/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.deployment;

/**
 * <p>this interface is used to inspect the scanned resource in scanner </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Inspector {

	/**
	 * @param name the class or resource to be inspected
	 */
	void inspect(String name);
}
