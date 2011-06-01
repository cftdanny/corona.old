/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.constantx;

/**
 * <p>The implementation service of Calculator </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class CalculatorImpl implements Calculator {

	/**
	 * {@inheritDoc}
	 * @see com.corona.test.context.constantx.Calculator#add(int, int)
	 */
	@Override
	public int add(final int a, final int b) {
		return a + b;
	}
}
