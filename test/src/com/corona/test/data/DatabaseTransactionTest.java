/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.data;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.mock.AbstractBusinessTest;

/**
 * <p>This test is used to test database transaction </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DatabaseTransactionTest extends AbstractBusinessTest {

	/**
	 * test commit
	 */
	@Test void testCommit() {

		// create first transaction and save data
		this.begin();
		HORDMST orderMaster = new HORDMST(this.getConnectionManager());
		
		TORDMST order = new TORDMST();
		order.setORDRNO("001");
		orderMaster.insert(order);
		this.commit();

		// create second transaction to test data
		orderMaster = new HORDMST(this.getConnectionManager());
		order = orderMaster.get(order.getORDRID());
		
		Assert.assertNotNull(order);
		Assert.assertEquals(order.getORDRNO(), "001");
	}

	/**
	 * test roll back
	 */
	@Test void testRollback() {

		// create first transaction and save data
		this.begin();
		HORDMST orderMaster = new HORDMST(this.getConnectionManager());
		
		TORDMST order = new TORDMST();
		order.setORDRNO("001");
		orderMaster.insert(order);
		this.rollback();

		// create second transaction to test data
		orderMaster = new HORDMST(this.getConnectionManager());
		order = orderMaster.get(order.getORDRID());
		
		Assert.assertNull(order);
	}
}
