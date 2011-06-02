/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.provider;

/**
 * <p>The inventory </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Inventory {

	/**
	 * @param in how many PCs in
	 * @param out how many PCs out
	 * @return how many PCs left in inventory
	 */
	int total(final int in, final int out);
}
