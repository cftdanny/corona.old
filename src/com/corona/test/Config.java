/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test;

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
	String TEST_CONFIG_FILE = "/testing.properties";
	
	/**
	 * this key will define all module classes that will be loaded for testing
	 */
	String TEST_MODULES_KEY = "test.module.classes";
	
	String WEB_APP_DESCRIPTOR = "web.app.descriptor";
	
	String WEB_CONTENT_PATh = "web.content.path";
	
	String WEB_CONTEXT_PATH = "web.context.path";
}
