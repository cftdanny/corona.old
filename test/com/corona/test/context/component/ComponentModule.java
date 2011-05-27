/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.component;

import com.corona.context.AbstractModule;
import com.corona.context.annotation.Application;
import com.corona.context.annotation.Context;

/**
 * <p>This module is used to test component </p>
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
		this.bind(DataRepository.class).to(DataRepositoryImpl.class).in(Context.class);
		
		this.bindConstant(Integer.class).as("first").to(1);
		this.bindConstant(Integer.class).as("second").to(2);
		this.bindConstant(int.class).as("third").to(3);
		
		this.bind(Adder.class).to(AdderImpl.class).alias("add");
		this.bindProvider(Substrator.class).to(SubstratorProvider.class).in(Application.class);
		
		this.bind(InjectContextManager.class).to(InjectContextManager.class);
		this.bindConfiguration(Adder.class).to("four").with(10);
	}
}
