/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.component;

import com.corona.context.AbstractModule;

/**
 * <p>This module is used to test constant component. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ComponentModule extends AbstractModule {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		// bind injected constant component to context 
		this.bindConstant(Integer.class).as("salary").to(200);
		this.bindConstant(Integer.class).as("houseRent").to(400);
		this.bindConstant(Integer.class).as("commission").to(300);
		
		// bind class component to context
		this.bind(Cost.class).to(CostImpl.class);
	}
}
