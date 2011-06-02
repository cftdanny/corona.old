/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.provider;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.context.Module;
import com.corona.mock.AbstractComponentTest;

/**
 * <p>This module is used to test class component </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ProviderComponentTest extends AbstractComponentTest {

	/**
	 * {@inheritDoc}
	 * @see com.corona.mock.AbstractComponentTest#getModules()
	 */
	@Override
	protected Module[] getModules() {
		return new Module[] {new ProviderModule()};
	}

	/**
	 * test class component
	 */
	@Test void testClassComponent() {

		Inventory inventory = this.get(Inventory.class);
		
		Assert.assertEquals(inventory, this.getContextManager().get("inventory"));
		Assert.assertEquals(inventory.total(200, 300), 150);
	}
}
