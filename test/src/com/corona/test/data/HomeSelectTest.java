/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.data;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.mock.AbstractBusinessTest;

/**
 * <p>This test is used to test list and count </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class HomeSelectTest extends AbstractBusinessTest {

	/**
	 * @throws Exception if fail
	 */
	@Test void testCount() throws Exception {
		
		// load data
		this.loadDatabase();
		
		// begin to test
		this.begin();
		HORDMST orderMaster = new HORDMST(this.getConnectionManager());
		Assert.assertEquals(orderMaster.count(), 3L);
		Assert.assertEquals(orderMaster.count("ORDRNO = ?", "001"), 1L);
		Assert.assertEquals(
				orderMaster.count("ORDRNO = :ORDRNO", new String[] {"ORDRNO"}, new Object[] {"001"}), 1L
		);
		this.commit();
	}
	
	/**
	 * @throws Exception if fail
	 */
	@Test void testList() throws Exception {
		// load data
		this.loadDatabase();
		
		// begin to test
		this.begin();
		HORDMST orderMaster = new HORDMST(this.getConnectionManager());
		Assert.assertEquals(orderMaster.list().size(), 3L);
		Assert.assertEquals(orderMaster.list("ORDRNO = ?", "001").size(), 1L);
		Assert.assertEquals(
				orderMaster.list("ORDRNO = :ORDRNO", new String[] {"ORDRNO"}, new Object[] {"001"}).size(), 1L
		);
		this.commit();
	}
}
