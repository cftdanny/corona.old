/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.data;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
import com.corona.data.DataException;
import com.corona.data.DataSourceManager;
import com.corona.data.Transaction;

/**
 * <p>This test is used to test{@link AbstractHome} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AbstractHomeTest {

	/**
	 * @return the new connection manager factory
	 * @throws DataException if fail
	 */
	private ConnectionManagerFactory getConnectionManagerFactory() throws DataException {
		return new DataSourceManager().create("HSQL", "jdbc:hsqldb:res:/test", "sa", "");
	}
	
	/**
	 * @return the new connection manager
	 * @throws DataException if fail
	 */
	private ConnectionManager getConnectionManager() throws DataException {
		return this.getConnectionManagerFactory().open();
	}

	/**
	 * @throws Exception if insert error
	 */
	@Test public void testInsert() throws Exception {
		
		ConnectionManager connectionManager = this.getConnectionManager();
		HORDMST hordmst = new HORDMST(connectionManager);
		
		Transaction transaction = connectionManager.getTransaction();
		transaction.begin();
		
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

		transaction.commit();
		
		transaction.begin();
		Assert.assertEquals(3, hordmst.count());
		Assert.assertEquals(1, hordmst.count("ORDRNO = ?", "0001"));
		
		TORDMST other = hordmst.get(tordmst.getORDRID());
		Assert.assertEquals(tordmst.getORDRNO(), other.getORDRNO());
		transaction.commit();

		transaction.begin();
		hordmst.delete(other.getORDRID());
		Assert.assertEquals(2, hordmst.count());
		transaction.commit();

		transaction.begin();
		List<TORDMST> orders = hordmst.list();
		Assert.assertEquals(2, orders.size());
		
		orders = hordmst.list("ORDRNO = ?", "0002");
		Assert.assertEquals(1, orders.size());

		orders = hordmst.list("ORDRNO = :ORDRNO", new String[] {"ORDRNO"},  new Object[] {"0002"});
		Assert.assertEquals(1, orders.size());

		orders = hordmst.list("ORDRNO = ?", "0003");
		Assert.assertEquals(0, orders.size());

		transaction.commit();
		
		connectionManager.close();
	}
	
	/**
	 * test AbstractHome.count
	 * @exception Exception if fail
	 */
	@Test public void testCount() throws Exception {
		
		ConnectionManager connectionManager = this.getConnectionManager();
		HORDMST hordmst = new HORDMST(connectionManager);
		
		Transaction transaction = connectionManager.getTransaction();
		transaction.begin();
		
		long count = hordmst.count();
		Assert.assertEquals(0, count);
		
		transaction.commit();
		connectionManager.close();
	}
}
