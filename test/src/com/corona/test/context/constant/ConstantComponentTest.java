/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.constant;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.context.Module;
import com.corona.mock.AbstractComponentTest;

/**
 * <p>This test case is used to test constant component </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ConstantComponentTest extends AbstractComponentTest {

	/**
	 * {@inheritDoc}
	 * @see com.corona.mock.AbstractComponentTest#getModules()
	 */
	@Override
	protected Module[] getModules() {
		return new Module[] {new ConstantModule()};
	}

	/**
	 * test constant component
	 */
	@Test public void testConstantComponent() {
		
		// check primitive component
		Assert.assertEquals(this.get(String.class, Constant.NAME), "1");
		Assert.assertEquals(this.get(Integer.class, Constant.NAME), (Integer) 1);
		Assert.assertEquals(this.get(Long.class, Constant.NAME), (Long) 1L);
		
		// check object component
		Constant constant = this.get(Constant.class);
		Assert.assertEquals(constant.getName(), "Apple");
		Assert.assertEquals(constant.getValue(), "MacBook");
		
		// check injected component, should can't inject
		Assert.assertNull(constant.getInteger());
	}
}
