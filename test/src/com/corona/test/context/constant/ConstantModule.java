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
		
		// bind primitive value to context 
		this.bindConstant(Integer.class).as(Constant.NAME).to(1);
		this.bindConstant(String.class).as(Constant.NAME).to("1");
		this.bindConstant(Long.class).as(Constant.NAME).to(1L);
		
		// bind object to context
		Constant constant = new Constant();
		constant.setName("Apple");
		constant.setValue("MacBook");
		
		this.bindConstant(Constant.class).to(constant);
	}
}
