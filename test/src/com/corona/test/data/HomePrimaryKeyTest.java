/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.data;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.mock.AbstractBusinessTest;

/**
 * <p>This test case is used to test primary key </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class HomePrimaryKeyTest extends AbstractBusinessTest {

	/**
	 * test PK: get
	 */
	@Test void get() {
		
		this.begin();
		HORDMST orderMaster = new HORDMST(this.getConnectionManager());
		Assert.assertNull(orderMaster.get(0));
		
		TORDMST order = new TORDMST();
		order.setORDRNO("001");
		orderMaster.insert(order);
		this.commit();
		
		Assert.assertNotNull(orderMaster.get(order.getORDRID()));
		
	}
	
	/**
	 * test PK: exists
	 */
	@Test void exists() {
		
		this.begin();
		HORDMST orderMaster = new HORDMST(this.getConnectionManager());
		Assert.assertFalse(orderMaster.exists(0));

		TORDMST order = new TORDMST();
		order.setORDRNO("001");
		orderMaster.insert(order);
		this.commit();
		
		Assert.assertTrue(orderMaster.exists(order.getORDRID()));
	}

	/**
	 * test PK: exists
	 */
	@Test void delete() {
		
		this.begin();
		HORDMST orderMaster = new HORDMST(this.getConnectionManager());

		// create a entity and insert it
		TORDMST order = new TORDMST();
		order.setORDRNO("001");
		orderMaster.insert(order);
		
		// should exist in database after insert
		Assert.assertTrue(orderMaster.exists(order.getORDRID()));
		
		// should not exist in database after delete
		orderMaster.delete(order.getORDRID());
		Assert.assertFalse(orderMaster.exists(order.getORDRID()));
		this.commit();
	}
	
	/**
	 * test PK: insert
	 */
	@Test void insert() {
		
		this.begin();
		HORDMST orderMaster = new HORDMST(this.getConnectionManager());

		// create a entity and insert it
		TORDMST order = new TORDMST();
		order.setORDRNO("001");
		orderMaster.insert(order);
		this.commit();
		
		// should exist in database after insert
		Assert.assertTrue(orderMaster.exists(order.getORDRID()));
	}

	/**
	 * test PK: insert
	 */
	@Test void update() {
		
		this.begin();
		HORDMST orderMaster = new HORDMST(this.getConnectionManager());

		// create a entity and insert it
		TORDMST order = new TORDMST();
		order.setORDRNO("001");
		orderMaster.insert(order);
		
		// update column to new value
		order.setORDRNO("002");
		orderMaster.update(order);
		
		// get entity from database again
		order = orderMaster.get(order.getORDRID());
		this.commit();
		
		// column value should changed in database
		Assert.assertEquals(order.getORDRNO(), "002");
	}
}
