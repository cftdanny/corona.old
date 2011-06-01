/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.component;

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
public class ClassComponentTest extends AbstractComponentTest {

	/**
	 * {@inheritDoc}
	 * @see com.corona.mock.AbstractComponentTest#getModules()
	 */
	@Override
	protected Module[] getModules() {
		return new Module[] {new ComponentModule()};
	}

	/**
	 * test class component
	 */
	@Test void testClassComponent() {

		Cost cost = this.get(Cost.class);
		Assert.assertEquals(cost.getPrice(300, 700), 100);
	}
}
