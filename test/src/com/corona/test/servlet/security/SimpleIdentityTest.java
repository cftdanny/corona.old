/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.security;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.corona.mock.AbstractWebsiteTest;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SimpleIdentityTest extends AbstractWebsiteTest {

	/**
	 * @throws Exception if fail
	 */
	@Test public void testSimpleIdentity() throws Exception {
		
		WebDriver driver = this.getWebDriver("/html/login.html");
		
		driver.findElement(By.id("username")).sendKeys("aaa");
		driver.findElement(By.id("password")).sendKeys("aaa");
		
		driver.findElement(By.id("login")).submit();
		
		// test should be logged page
		Assert.assertEquals("Logged", driver.getTitle());
		Assert.assertEquals("aaa", driver.findElement(By.id("username")).getText());
		
		// click to log out
		driver.findElement(By.id("logout")).submit();

		// should be log in page
		Assert.assertEquals("Login", driver.getTitle());
		
		// get logged page again
		driver = this.getWebDriver("/html/logged.html");
		Assert.assertEquals("", driver.findElement(By.id("username")).getText());
	}
}
