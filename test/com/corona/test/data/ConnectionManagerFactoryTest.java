/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.data;

import junit.framework.Assert;

import org.junit.Test;

import com.corona.context.ContextManager;
import com.corona.context.Initializer;
import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
import com.corona.data.DataException;
import com.corona.data.DataSourceManager;
import com.corona.data.ResultHandler;
import com.corona.data.Transaction;

/**
 * <p>This test is used to test create ConnectionManagerFactory, ConnectionManager and Transaction. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ConnectionManagerFactoryTest {

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
	 * @exception Exception If fail
	 */
	@Test public void testCreateConnectionManager() throws Exception {

		// create new connection manager with testing database
		ConnectionManager connectionManager = this.getConnectionManager();
		
		// create transaction in order to control resource in database 
		Transaction transaction = connectionManager.getTransaction();
		
		// count an empty test in database between transaction
		transaction.begin();
		int count = connectionManager.createQuery(ResultHandler.INTEGER, "SELECT COUNT(*) FROM TEMPTDB").get();
		transaction.rollback();
		
		// close connection manager
		connectionManager.close();
		
		// the row count from empty table should be 0
		Assert.assertEquals(0, count);
	}
	
	/**
	 * test resolve connection manager from container
	 */
	@Test public void testResolveFromContainer() {
		
		ContextManager contextManager = Initializer.build(new DatabaseModule()).create();
		ConnectionManager connectionManager = contextManager.get(ConnectionManager.class);

		// create transaction in order to control resource in database 
		Transaction transaction = connectionManager.getTransaction();
		
		// count an empty test in database between transaction
		transaction.begin();
		int count = connectionManager.createQuery(ResultHandler.INTEGER, "SELECT COUNT(*) FROM TEMPTDB").get();
		transaction.rollback();
		
		// close connection manager
		connectionManager.close();
		
		// the row count from empty table should be 0
		Assert.assertEquals(0, count);
	}
}
