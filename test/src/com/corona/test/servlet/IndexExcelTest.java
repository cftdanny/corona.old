/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import org.testng.annotations.Test;

import com.corona.mock.AbstractWebsiteTest;

/**
 * <p>TEST PAGE: /index.xls </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class IndexExcelTest extends AbstractWebsiteTest {

	/**
	 * @throws Exception if throws exception
	 */
	@Test public void index() throws Exception {
		this.getWebDriver("/index.xls");
	}
}
