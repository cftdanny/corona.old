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
		
		String str = "no=001&qty=20&total.count=3&total.sum=80";
		WebDriver driver1 = this.getWebDriver("/params.html?" + str);
		
		Assert.assertTrue(driver1.getTitle().startsWith("Inject Parameter Map"));
		Assert.assertEquals(driver1.findElement(By.id("no")).getText(), "001");
		Assert.assertEquals(driver1.findElement(By.id("qty")).getText(), "20");
		Assert.assertEquals(driver1.findElement(By.id("count")).getText(), "3");
		Assert.assertEquals(driver1.findElement(By.id("sum")).getText(), "80");
	}

	/**
	 * @throws Exception if fail
	 */
	@Test public void testArrayObject() throws Exception {
		
		String str = "mails[0]=danny&mails[1]=chen";
		str = str + "&items[0].name=A01&items[0].price=10";
		str = str + "&items[1].name=A02&items[1].price=20";
		WebDriver driver1 = this.getWebDriver("/params.html?" + str);
		
		Assert.assertEquals(driver1.findElement(By.id("mail")).getText(), "[danny, chen]");
		Assert.assertEquals(driver1.findElement(By.id("item:A01")).getText(), "10");
		Assert.assertEquals(driver1.findElement(By.id("item:A02")).getText(), "20");
	}

	/**
	 * @throws Exception if fail
	 */
	@Test public void testComplexObject() throws Exception {
		
		String str = "mails[0]=danny&mails[1]=chen";
		str = str + "&items[0].name=A01&items[0].price=10";
		str = str + "&items[0].lines[0].to=SHA1&items[0].lines[0].notes[0]=SHA-11&items[0].lines[0].notes[1]=SHA-12";
		str = str + "&items[0].lines[1].to=SHA2&items[0].lines[1].notes[0]=SHA-11&items[0].lines[1].notes[1]=SHA-12";
		str = str + "&items[1].name=A02&items[1].price=20";
		WebDriver driver1 = this.getWebDriver("/params.html?" + str);
		
		Assert.assertEquals(driver1.findElement(By.id("mail")).getText(), "[danny, chen]");
		Assert.assertEquals(driver1.findElement(By.id("item:A01")).getText(), "10");
		Assert.assertEquals(driver1.findElement(By.id("item:A02")).getText(), "20");
	}
}
