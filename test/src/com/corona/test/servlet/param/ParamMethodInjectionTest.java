/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.param;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.mock.AbstractWebsiteTest;

/**
 * <p>This test case is used to test simple type injection </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ParamMethodInjectionTest extends AbstractWebsiteTest {

	/**
	 * @throws Exception if error
	 */
	@Test public void testSimpleParam() throws Exception {
	
		WebDriver driver1 = this.getWebDriver("/param/match/AA/10/CC.html");
		
		driver1.getTitle();
		Assert.assertEquals(driver1.findElement(By.id("a")).getText(), "AA");
		Assert.assertEquals(driver1.findElement(By.id("b")).getText(), "10");
		Assert.assertEquals(driver1.findElement(By.id("c")).getText(), "CC");
	}
}
