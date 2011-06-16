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
public class ParamBeanInjectionTest extends AbstractWebsiteTest {

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
		str = str + "&items[0].lines[1].to=SHA2&items[0].lines[1].notes[0]=SHA-21&items[0].lines[1].notes[1]=SHA-22";
		str = str + "&items[1].name=A02&items[1].price=20";
		str = str + "&items[1].lines[0].to=SHA3&items[1].lines[0].notes[0]=SHA-31&items[1].lines[0].notes[1]=SHA-32";
		str = str + "&items[1].lines[1].to=SHA4&items[1].lines[1].notes[0]=SHA-41&items[1].lines[1].notes[1]=SHA-42";
		WebDriver driver1 = this.getWebDriver("/params.html?" + str);
		
		Assert.assertEquals(driver1.findElement(By.id("mail")).getText(), "[danny, chen]");
		Assert.assertEquals(driver1.findElement(By.id("item:A01")).getText(), "10");
		Assert.assertEquals(driver1.findElement(By.id("item:A02")).getText(), "20");
		Assert.assertEquals(driver1.findElement(By.id("line:SHA1")).getText(), "[SHA-11, SHA-12]");
		Assert.assertEquals(driver1.findElement(By.id("line:SHA2")).getText(), "[SHA-21, SHA-22]");
		Assert.assertEquals(driver1.findElement(By.id("line:SHA3")).getText(), "[SHA-31, SHA-32]");
		Assert.assertEquals(driver1.findElement(By.id("line:SHA4")).getText(), "[SHA-41, SHA-42]");
	}
}
