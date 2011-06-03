/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.mock.AbstractWebsiteTest;

/**
 * <p>This test is used to test cookie </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class CookieHtmlTest extends AbstractWebsiteTest {

	/**
	 * @throws Exception if fail
	 */
	@Test public void cookie() throws Exception {
		
		WebDriver driver = this.getWebDriver("/cookie.html");
		
		// 1: Before submit, test it with empty
		Assert.assertEquals(driver.findElement(By.id("hello")).getText(), "");
		Assert.assertEquals(driver.findElement(By.id("world")).getText(), "");
		
		driver.findElement(By.id("inputHello")).sendKeys("[hello]");
		driver.findElement(By.id("inputWorld")).sendKeys("{world}");
		driver.findElement(By.id("submit")).submit();

		// 2: Value should store to cookie
		Assert.assertEquals(driver.findElement(By.id("hello")).getText(), "[hello]");
		Assert.assertEquals(driver.findElement(By.id("world")).getText(), "{world}");

		driver.findElement(By.id("inputHello")).sendKeys("");
		driver.findElement(By.id("inputWorld")).sendKeys("");
		driver.findElement(By.id("submit")).submit();

		// 3: Value get from cookie
		Assert.assertEquals(driver.findElement(By.id("hello")).getText(), "[hello]");
		Assert.assertEquals(driver.findElement(By.id("world")).getText(), "{world}");
	}
}
