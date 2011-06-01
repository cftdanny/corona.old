/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.componentx;

import com.corona.context.annotation.Create;
import com.corona.context.annotation.Install;
import com.corona.context.annotation.Inject;
import com.corona.context.annotation.Name;

/**
 * <p>The implementation of adder </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Install(dependencies = "java.lang.String")
public class AdderImpl implements Adder {

	/**
	 * the first
	 */
	@Inject("first") private Integer first = 0;
	
	/**
	 * the second
	 */
	private Integer second = 0;
	
	/**
	 * the third
	 */
	private Integer third = 0;
	
	/**
	 * the four
	 */
	private Integer four = 0;
	
	/**
	 * @param third the third
	 */
	@Inject public AdderImpl(@Name("third") final int third) {
		this.third = third;
	}
	
	/**
	 * the create method
	 */
	@Create public void create() {
		this.first = this.first + this.second - this.second;
	}
	
	/**
	 * @return the four
	 */
	public Integer getFour() {
		return four;
	}
	
	/**
	 * @param four the four to set
	 */
	public void setFour(final Integer four) {
		this.four = four;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.test.context.componentx.Adder#add(int, int)
	 */
	@Override
	public int add(final int a, final int b) {
		return a + b + this.first + this.second + this.third;
	}

	/**
	 * @param second the second to set
	 */
	@Inject public void setSecond(@Name("second") final Integer second) {
		this.second = second;
	}
}
