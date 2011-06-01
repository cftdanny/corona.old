/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.component;

/**
 * <p>Test component to calculate price </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Cost {

	/**
	 * @param spend how much money bought this product
	 * @param sell how much money sold this product
	 * @return the price of this product
	 */
	int getPrice(int spend, int sell);
}
