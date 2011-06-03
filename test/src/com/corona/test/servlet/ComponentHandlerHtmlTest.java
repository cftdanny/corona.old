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
public class ComponentHandlerHtmlTest extends AbstractWebsiteTest {

	/**
	 * @throws Exception if throws exception
	 */
	@Test public void index() throws Exception {
		
		WebDriver driver1 = this.getWebDriver("/child/1.txt");
		Assert.assertTrue(driver1.getPageSource().startsWith("Child Page 1!"));
		
		WebDriver driver2 = this.getWebDriver("/child/2.txt");
		Assert.assertTrue(driver2.getPageSource().startsWith("Child Page 2!"));
	}
}
