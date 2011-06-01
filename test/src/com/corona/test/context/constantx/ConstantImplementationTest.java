/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.constantx;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.corona.context.ContextManager;
import com.corona.context.Initializer;

/**
 * <p>This test case is used to test constant component </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ConstantImplementationTest {

	/**
	 * the context manager
	 */
	private ContextManager contextManager;
	
	/**
	 * initialize test case
	 */
	@Before	public void init() {
		this.contextManager = Initializer.build(new ConstantModule()).create();
	}
	
	/**
	 * test constant component
	 */
	@Test public void testConstantComponent() {
		
		Assert.assertEquals("1", this.contextManager.get(String.class, Constants.NAME));
		Assert.assertEquals((Integer) 1, this.contextManager.get(Integer.class, Constants.NAME));
		Assert.assertEquals((Long) 1L, this.contextManager.get(Long.class, Constants.NAME));
		
		Computer computer = this.contextManager.get(Computer.class);
		Assert.assertEquals("Apple", computer.getBrand());
		Assert.assertEquals("MacBook", computer.getModel());
		
		Calculator calculator = this.contextManager.get(Calculator.class, Constants.NAME);
		Assert.assertEquals(3, calculator.add(1, 2));
	}
}
