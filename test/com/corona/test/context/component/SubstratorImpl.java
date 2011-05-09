/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.component;

/**
 * <p>the test component for provider </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SubstratorImpl implements Substrator {

	/**
	 * the first
	 */
	private int first;
	
	/**
	 * the second
	 */
	private int second;
	
	/**
	 * the third
	 */
	private int third;
	
	/**
	 * @param first the first
	 * @param second the second
	 * @param third the third
	 */
	public SubstratorImpl(final int first, final int second, final int third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.test.context.component.Substrator#substrate(int, int)
	 */
	@Override
	public int substrate(final int a, final int b) {
		return a - b - first - second - third;
	}
}
