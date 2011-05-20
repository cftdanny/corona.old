/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.data;

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
