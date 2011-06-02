/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.mock.AbstractWebsiteTest;

/**
 * <p>TEST PAGE: /index.html </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class IndexHtmlTest extends AbstractWebsiteTest {

	/**
	 * @throws Exception if throws exception
	 */
	@Test public void index() throws Exception {
		
		WebDriver driver = this.getWebDriver("/index.html");
		
		Assert.assertEquals("Corona Web Framework (CWF)", driver.getTitle());
		WebElement element = driver.findElement(By.id("caption"));
		Assert.assertEquals("Welcome to Corona Web Framework (CWF)", element.getText());
	}
}
