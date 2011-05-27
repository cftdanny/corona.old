/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
	 * the configuration file for testing
	 */
	private static final String TEST_CONFIG_FILE = "/testing.properties";
	
	/**
	 * the key in properties file to load testing modules
	 */
	private static final String TEST_MODULES_KEY = "testing.modules";
	
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
		
		// load testing configuration file from class path
		Properties properties = new Properties();
		InputStream in = this.getClass().getResourceAsStream(this.getTestConfigFile());
		if (in != null) {
			properties.load(in);
		}
		
		// get testing modules and try to loaded it
		List<Module> modules = new ArrayList<Module>();
		if (properties.contains(TEST_MODULES_KEY)) {
			for (String className : properties.get(TEST_MODULES_KEY).toString().split(",")) {
				modules.add((Module) Class.forName(className).newInstance());
			}
		}

		// merge all modules that defines for this test
		for (Module module : this.getModules()) {
			modules.add(module);
		}
		
		// create context manager factory by production and testing modules
		this.contextManagerFactory = Initializer.build(modules);
	}
	
	/**
	 * before test, will try to create context manager
	 */
	@BeforeMethod public void before() {
		this.contextManager = this.contextManagerFactory.create(this.getContext());
	}
	
	/**
	 * release context manager resources and destroy it
	 */
	@AfterMethod public void after() {
		this.contextManager.close();
		this.contextManager = null;
	}
	
	/**
	 * @return the configuration file for testing
	 */
	protected String getTestConfigFile() {
		return TEST_CONFIG_FILE;
	}
	
	/**
	 * @return all modules for this testing
	 */
	protected Module[] getModules() {
		return new Module[] {};
	}
	
	/**
	 * @return the predefined variables or context that will merge to new created context manager
	 */
	@SuppressWarnings("rawtypes")
	protected Map<Key, Object> getContext() {
		return new HashMap<Key, Object>();
	}
	
	/**
	 * @return the context manager factory
	 */
	public ContextManagerFactory getContextManagerFactory() {
		return this.contextManagerFactory;
	}
	
	/**
	 * @return the current context manager
	 */
	public ContextManager getContextManager() {
		return contextManager;
	}
	
	/**
	 * @param <T> the component type
	 * @param type the class of component type
	 * @return the instance of component
	 */
	public <T> T get(final Class<T> type) {
		return this.contextManager.get(type);
	}
	
	/**
	 * @param <T> the component type
	 * @param type the class of component type
	 * @param name the component name
	 * @return the instance of component
	 */
	public <T> T get(final Class<T> type, final String name) {
		return this.contextManager.get(type, name);
	}

	/**
	 * @param alias the component alias
	 * @return the instance of component
	 */
	public Object get(final String alias) {
		return this.contextManager.get(alias);
	}
}
