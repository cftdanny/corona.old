/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.component;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.corona.context.ContextManager;
import com.corona.context.Initializer;

/**
 * <p>This test is used to test component container </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ComponentImplementationTest {

	/**
	 * the context manager
	 */
	private ContextManager contextManager;
	
	/**
	 * initialize test case
	 */
	@Before	public void init() {
		this.contextManager = Initializer.build(new ComponentModule()).create();
	}
	
	/**
	 * test very simple component
	 */
	@Test public void testSimpleComponent() {
		
		DataRepository repository = this.contextManager.get(DataRepository.class, "default");
		Assert.assertEquals("A", repository.find(1));
	}
	
	/**
	 * test component with injection
	 */
	@Test public void testInjectionComponent() {
		
		Adder adder = this.contextManager.get(Adder.class);
		Assert.assertEquals(9, adder.add(1, 2));
	}
	
	/**
	 * test provider with inject
	 */
	@Test public void testInjectProvider() {
		Substrator substrator = this.contextManager.get(Substrator.class);
		Assert.assertEquals(3, substrator.substrate(10, 1));
	}
	
	/**
	 * test alias
	 */
	@Test public void testAlias() {
		Adder adder = (Adder) this.contextManager.get("add");
		Assert.assertEquals(9, adder.add(1, 2));
	}
	
	/**
	 * test inject context manager
	 */
	@Test public void testInjectContextManager() {
		
		InjectContextManager injector = this.contextManager.get(InjectContextManager.class);
		Assert.assertEquals(this.contextManager, injector.getContextManager());
	}

	/**
	 * test setting value to component
	 */
	@Test public void testBindSetting() {
		
		Adder adder = (Adder) this.contextManager.get("add");
		Assert.assertEquals(10, ((AdderImpl) adder).getFour().intValue());
	}
}
