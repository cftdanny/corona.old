/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.constant;

import com.corona.context.AbstractModule;

/**
 * <p>This module is used to test constant component. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ConstantModule extends AbstractModule {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		this.bindConstant(Integer.class).as(Constants.NAME).to(1);
		this.bindConstant(String.class).as(Constants.NAME).to("1");
		this.bindConstant(Long.class).as(Constants.NAME).to(1L);
		this.bindConstant(Calculator.class).as(Constants.NAME).to(new CalculatorImpl());
		
		Computer computer = new Computer();
		computer.setBrand("Apple");
		computer.setModel("MacBook");
		
		this.bindConstant(Computer.class).to(computer);
	}
}
