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
public class ParamSimpleInjectionTest extends AbstractWebsiteTest {

	/**
	 * @throws Exception if error
	 */
	@Test public void testSimpleParam() throws Exception {
	
		String str = "a=ABC&b=10&b=20&c=DEF&c=HIJ&c=KLM&d=30";
		WebDriver driver1 = this.getWebDriver("/param/simple.html?" + str);
		
		driver1.getTitle();
		Assert.assertEquals(driver1.findElement(By.id("a")).getText(), "ABC");
		Assert.assertEquals(driver1.findElement(By.id("b")).getText(), "[10, 20]");
		Assert.assertEquals(driver1.findElement(By.id("c")).getText(), "DEFHIJKLM");
		Assert.assertEquals(driver1.findElement(By.id("d")).getText(), "30");
	}
}
