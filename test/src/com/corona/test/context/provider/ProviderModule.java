/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.provider;

import com.corona.context.AbstractModule;

/**
 * <p>This module is used to test provider component. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ProviderModule extends AbstractModule {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		// bind injected constant component to context 
		this.bindConstant(Integer.class).as("current").to(200);
		this.bindConstant(Integer.class).as("lost").to(50);
		this.bindConstant(Integer.class).as("returned").to(100);
		
		// bind class component to context
		this.bindProvider(Inventory.class).to(InventoryProvider.class);
	}
}
