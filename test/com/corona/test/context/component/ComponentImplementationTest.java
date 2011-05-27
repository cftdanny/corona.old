/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.component;

import org.testng.annotations.Test;

import junit.framework.Assert;

import com.corona.context.Module;
import com.corona.test.AbstractComponentTest;

/**
 * <p>This test is used to test component container </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ComponentImplementationTest extends AbstractComponentTest {

	/**
	 * {@inheritDoc}
	 * @see com.corona.test.AbstractComponentTest#getModules()
	 */
	@Override
	protected Module[] getModules() {
		return new Module[] {new ComponentModule()};
	}
	
	/**
	 * test very simple component
	 */
	@Test public void testSimpleComponent() {
		
		DataRepository repository = this.get(DataRepository.class, "default");
		Assert.assertEquals("A", repository.find(1));
	}
}
