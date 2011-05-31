/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.demo.context.constant;

/**
 * <p>The implementation service of Calculator </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class CalculatorImpl implements Calculator {

	/**
	 * {@inheritDoc}
	 * @see com.corona.demo.context.constant.Calculator#add(int, int)
	 */
	@Override
	public int add(final int a, final int b) {
		return a + b;
	}
}
