/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.corona.context.Module;
import com.corona.util.StringUtil;

/**
 * <p>The configuration key about unit test. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class Config {

	/**
	 * the testing configuration files for all unit test
	 */
	private static final String TEST_CONFIG_FILE = "/config-test.properties";
	
	/**
	 * the key will define all module classes that will be loaded for testing
	 */
	private static final String TEST_MODULES_CLASSES = "test.module.classes";
	
	/**
	 * the key is used to get web app descriptor for testing web application 
	 */
	private static final String WEB_APP_DESCRIPTOR = "web.app.descriptor";
	
	/**
	 * the key is used to get web app content path for testing web application
	 */
	private static final String WEB_CONTENT_PATH = "web.content.path";
	
	/**
	 * the key is used to get web context path for testing web application
	 */
	private static final String WEB_CONTEXT_PATH = "web.context.path";
	
	/**
	 * the key is used to get web driver
	 */
	private static final String WEB_DRIVER_NAME = "web.driver.name";
	
	/**
	 * the properties for testing configuration
	 */
	private Properties properties = new Properties();
	
	/**
	 * @param configFileName file name for testing configuration
	 */
	protected Config(final String configFileName) {
		
		String fileName = configFileName;
		if (StringUtil.isBlank(configFileName)) {
			fileName = Config.TEST_CONFIG_FILE;
		}
		
		try {
			this.properties.load(this.getClass().getResourceAsStream(fileName));
		} catch (IOException e) {
			this.properties = new Properties();
		}
	}
	
	/**
	 * @return the configuration
	 */
	static Config laod() {
		return new Config(Config.TEST_CONFIG_FILE);
	}
	
	/**
	 * @param configFileName file name for testing configuration
	 * @return the configuration
	 */
	static Config laod(final String configFileName) {
		return new Config(configFileName);
	}
	
	/**
	 * @return the class names of configuration modules for testing
	 */
	String getModuleClassNames() {
		return this.properties.getProperty(Config.TEST_MODULES_CLASSES);
	}
	
	/**
	 * @return all modules that are defined in testing configuration file 
	 * @throws Exception if fail to load modules
	 */
	Module[] getModules() throws Exception {
		
		String classNames = this.properties.getProperty(Config.TEST_MODULES_CLASSES);
		if (StringUtil.isBlank(classNames)) {
			
			List<Module> modules = new ArrayList<Module>();
			for (String className : classNames.split(",")) {
				modules.add((Module) Class.forName(className).newInstance());
			}
			return modules.toArray(new Module[0]);
		} else {
			return new Module[0];
		}
	}
	
	/**
	 * @return the file for web app descriptor
	 */
	String getWebAppDescriptor() {
		
		String descriptor = (String) this.properties.get(Config.WEB_APP_DESCRIPTOR);
		if (StringUtil.isBlank(descriptor)) {
			descriptor = "./WEB-INF/web.xml";
		}
		return descriptor;
	}
	
	/**
	 * @return the web content path for web application
	 */
	String getWebContentPath() {
		
		String path = (String) this.properties.get(Config.WEB_CONTENT_PATH);
		if (StringUtil.isBlank(path)) {
			path = "./WebContent";
		}
		return path;
	}
	
	/**
	 * @return the web content path for web application
	 */
	String getWebContextPath() {
		
		String path = (String) this.properties.get(Config.WEB_CONTEXT_PATH);
		if (StringUtil.isBlank(path)) {
			path = "/";
		}
		return path;
	}
	
	/**
	 * @return the selenium web driver name
	 */
	String getWebDriverName() {
		
		String name = (String) this.properties.get(Config.WEB_DRIVER_NAME);
		if (StringUtil.isBlank(name)) {
			name = "HtmlUnit";
		}
		return name;
	}
}
