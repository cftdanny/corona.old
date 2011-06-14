/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.param;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.mock.AbstractWebsiteTest;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ParamsInjectionTest extends AbstractWebsiteTest {

	/**
	 * @throws Exception if fail
	 */
	@Test public void testSimpleObject() throws Exception {
		
		WebDriver driver1 = this.getWebDriver("/params.html");
		Assert.assertTrue(driver1.getTitle().startsWith("Inject Parameter Map"));

	}
}
