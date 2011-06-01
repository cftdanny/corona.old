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
		Assert.assertEquals("1", this.get(String.class, Constant.NAME));
		Assert.assertEquals((Integer) 1, this.get(Integer.class, Constant.NAME));
		Assert.assertEquals((Long) 1L, this.get(Long.class, Constant.NAME));
		
		// check object component
		Constant constant = this.get(Constant.class);
		Assert.assertEquals("Apple", constant.getName());
		Assert.assertEquals("MacBook", constant.getValue());
	}
}
