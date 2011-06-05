/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.data;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.data.ResultHandler;
import com.corona.mock.AbstractBusinessTest;

/**
 * <p>This test is used to test result handler </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ResultHandlerTest extends AbstractBusinessTest {

	/**
	 * @throws Exception if fail
	 */
	@Test void testLongResultHandler() throws Exception {
		this.loadDatabase();
		
		this.begin();
		List<Long> result = this.getConnectionManager().createQuery(
				ResultHandler.LONG, "SELECT ORDRID FROM TORDMST"
		).list();
		this.rollback();
		
		Assert.assertEquals(result.size(), 3);
		Assert.assertTrue(result.contains(0L));
		Assert.assertTrue(result.contains(1L));
		Assert.assertTrue(result.contains(2L));
	}

	/**
	 * @throws Exception if fail
	 */
	@Test void testStringResultHandler() throws Exception {
		this.loadDatabase();
		
		this.begin();
		List<String> result = this.getConnectionManager().createQuery(
				ResultHandler.STRING, "SELECT ORDRNO FROM TORDMST ORDER BY ORDRNO"
		).list();
		this.rollback();
		
		Assert.assertEquals(result.size(), 3);
		Assert.assertEquals(result.get(0).trim(), "001");
		Assert.assertEquals(result.get(1).trim(), "002");
		Assert.assertEquals(result.get(2).trim(), "003");
	}
}
