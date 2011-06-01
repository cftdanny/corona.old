/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.mock;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.Initializer;
import com.corona.context.Key;
import com.corona.context.Module;

/**
 * <p>This test is used to test component without database and transaction supporting. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AbstractComponentTest {

	/**
	 * the testing configuration
	 */
	private Config config;

	/**
	 * the current context manager factory
	 */
	private ContextManagerFactory contextManagerFactory;
	
	/**
	 * the current context manager
	 */
	private ContextManager contextManager;
	
	/**
	 * this method is used to create context manager factory
	 * @throws Exception Fail to load test configuration file
	 */
	@BeforeClass public void startup() throws Exception {
		
		// load testing configuration 
		this.config = Config.laod(this.getConfigFileName());
		
		// determine the modules that are used to run test
		Module[] modules = this.getModules();
		if ((modules == null) || (modules.length == 0)) {
			modules = this.config.getModules();
		}
		this.contextManagerFactory = Initializer.build(modules);
	}
	
	/**
	 * before test, will try to create context manager
	 */
	@BeforeMethod public void before() {
		this.contextManager = this.contextManagerFactory.create(this.getVariableMap());
	}
	
	/**
	 * release context manager resources and destroy it
	 */
	@AfterMethod public void after() {
		this.contextManager.close();
		this.contextManager = null;
	}
	
	/**
	 * @return the testing configuration
	 */
	protected Config getConfig() {
		return config;
	}

	/**
	 * @return the configuration file for testing
	 */
	protected String getConfigFileName() {
		return null;
	}
	
	/**
	 * @return all modules for this testing
	 */
	protected Module[] getModules() {
		return null;
	}
	
	/**
	 * @return the predefined variables or context that will merge to new created context manager
	 */
	@SuppressWarnings("rawtypes")
	protected Map<Key, Object> getVariableMap() {
		return new HashMap<Key, Object>();
	}
	
	/**
	 * @return the context manager factory
	 */
	protected ContextManagerFactory getContextManagerFactory() {
		return this.contextManagerFactory;
	}
	
	/**
	 * @return the current context manager
	 */
	protected ContextManager getContextManager() {
		return contextManager;
	}
	
	/**
	 * @param <T> the component type
	 * @param type the class of component type
	 * @return the instance of component
	 */
	protected <T> T get(final Class<T> type) {
		return this.contextManager.get(type);
	}
	
	/**
	 * @param <T> the component type
	 * @param type the class of component type
	 * @param name the component name
	 * @return the instance of component
	 */
	protected <T> T get(final Class<T> type, final String name) {
		return this.contextManager.get(type, name);
	}
}
