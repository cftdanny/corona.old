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
public class IndexTextTest extends AbstractWebsiteTest {

	/**
	 * @throws Exception if throws exception
	 */
	@Test public void index() throws Exception {
		
		WebDriver driver = this.getWebDriver("/index.txt");
		Assert.assertTrue(driver.getPageSource().startsWith("Hello, Corona!"));
	}
}
