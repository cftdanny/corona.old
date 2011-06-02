/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.mock.AbstractWebsiteTest;

/**
 * <p>TEST PAGE: /index.pdf </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class IndexJsonTest extends AbstractWebsiteTest {

	/**
	 * @throws Exception if throws exception
	 */
	@Test public void index() throws Exception {
		
		WebDriver driver = this.getWebDriver("/index.json");
		
		Assert.assertTrue(driver.getPageSource().contains("result"));
		Assert.assertTrue(driver.getPageSource().contains("user"));
		Assert.assertTrue(driver.getPageSource().contains("password"));
	}
}
