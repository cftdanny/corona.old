/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test;

import org.testng.annotations.BeforeClass;

import com.corona.context.Key;
import com.corona.data.ConnectionManager;
import com.corona.data.Transaction;
import com.corona.data.TransactionManager;

/**
 * <p>This test is used to test business object. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AbstractBusinessTest extends AbstractComponentTest {

	/**
	 * the current connection manager
	 */
	private ConnectionManager connectionManager;
	
	/**
	 * the current transaction
	 */
	private Transaction transaction;

	/**
	 * {@inheritDoc}
	 * @see com.corona.test.AbstractComponentTest#before()
	 */
	@Override
	@BeforeClass public void before() {
		super.before();
		
		// open connection manager
		this.connectionManager = this.get(ConnectionManager.class);
		
		// try to find transaction and start it
		TransactionManager transactionManager = this.getContextManager().get(
				new Key<TransactionManager>(TransactionManager.class), false
		);
		if (transactionManager != null) {
			this.transaction = transactionManager.getTransaction();
		} else {
			this.transaction = this.connectionManager.getTransaction();
		}
		
		// start transaction in order to control data source
		this.transaction.begin();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.test.AbstractComponentTest#after()
	 */
	@Override
	public void after() {
		super.after();
		
		// commit transaction
		if (this.transaction.getRollbackOnly()) {
			this.transaction.rollback();
		} else {
			this.transaction.commit();
		}
		this.transaction = null;
		
		// close connection manager
		this.connectionManager.close();
		this.connectionManager = null;
	}
	
	/**
	 * @return the current connection manager
	 */
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}
	/**
	 * @return the current transaction
	 */
	public Transaction getTransaction() {
		return transaction;
	}
}
