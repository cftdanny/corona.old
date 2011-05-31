/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.demo.data;

import java.util.List;

import org.testng.annotations.Test;

import junit.framework.Assert;

import com.corona.context.Module;
import com.corona.data.ConnectionManager;
import com.corona.test.AbstractBusinessTest;

/**
 * <p>This test is used to test{@link AbstractHome} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AbstractHomeTest extends AbstractBusinessTest {

	/**
	 * {@inheritDoc}
	 * @see com.corona.test.AbstractComponentTest#getModules()
	 */
	@Override
	protected Module[] getModules() {
		return new Module[] {new TestingDataSourceModule()};
	}

	/**
	 * @throws Exception if insert error
	 */
	@Test public void testInsert() throws Exception {
		
		ConnectionManager connectionManager = this.getConnectionManager();
		HORDMST hordmst = new HORDMST(connectionManager);
		
		TORDMST tordmst = new TORDMST();
		tordmst.setORDRNO("0001");
		hordmst.insert(tordmst);
		
		Assert.assertNotNull(tordmst.getORDRID());

		tordmst = new TORDMST();
		tordmst.setORDRNO("0002");
		hordmst.insert(tordmst);

		tordmst = new TORDMST();
		tordmst.setORDRNO("0003");
		hordmst.insert(tordmst);

		this.getTransaction().commit();
		
		this.getTransaction().begin();
		Assert.assertEquals(3, hordmst.count());
		Assert.assertEquals(1, hordmst.count("ORDRNO = ?", "0001"));
		
		TORDMST other = hordmst.get(tordmst.getORDRID());
		Assert.assertEquals(tordmst.getORDRNO(), other.getORDRNO());
		this.getTransaction().commit();

		this.getTransaction().begin();
		hordmst.delete(other.getORDRID());
		Assert.assertEquals(2, hordmst.count());
		this.getTransaction().commit();

		this.getTransaction().begin();
		List<TORDMST> orders = hordmst.list();
		Assert.assertEquals(2, orders.size());
		
		orders = hordmst.list("ORDRNO = ?", "0002");
		Assert.assertEquals(1, orders.size());

		orders = hordmst.list("ORDRNO = :ORDRNO", new String[] {"ORDRNO"},  new Object[] {"0002"});
		Assert.assertEquals(1, orders.size());

		orders = hordmst.list("ORDRNO = ?", "0003");
		Assert.assertEquals(0, orders.size());

		hordmst.close();
	}
	
	/**
	 * test AbstractHome.count
	 * @exception Exception if fail
	 */
	@Test public void testCount() throws Exception {
		
		HORDMST hordmst = new HORDMST(this.getConnectionManager());
		
		long count = hordmst.count();
		Assert.assertEquals(0, count);
	}
}
