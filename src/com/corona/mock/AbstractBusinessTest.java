/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.mock;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.corona.context.Key;
import com.corona.data.ConnectionManager;
import com.corona.data.Transaction;
import com.corona.data.TransactionManager;

/**
 * <p>This test is used to test business object that control data source information. </p>
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
	 * @see com.corona.mock.AbstractComponentTest#before()
	 */
	@Override
	@BeforeMethod public void before() {
		super.before();
		this.connectionManager = this.get(ConnectionManager.class);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.mock.AbstractComponentTest#after()
	 */
	@Override
	@AfterMethod public void after() {
		
		// close connection manager
		this.connectionManager.close();
		this.connectionManager = null;
		
		// call method after in super class to release resources 
		super.after();
	}
	
	/**
	 * @return JTA transaction manager 
	 */
	private TransactionManager getTransactionManager() {
		return this.getContextManager().get(new Key<TransactionManager>(TransactionManager.class), false);
	}
	
	/**
	 * create a new transaction
	 */
	protected void begin() {
		
		// check if transaction is created or not. If yes, throw exception
		if (this.transaction != null) {
			throw new IllegalStateException("Transaction is created already");
		}
		
		// try to find transaction and start it
		TransactionManager transactionManager = this.getTransactionManager();
		if (transactionManager != null) {
			this.transaction = transactionManager.getTransaction();
		} else {
			this.transaction = this.connectionManager.getTransaction();
		}
		
		// start transaction in order to control data source
		this.transaction.begin();
	}
	
	/**
	 * commit transaction
	 */
	protected void commit() {
		this.transaction.commit();
		this.transaction = null;
	}
	
	/**
	 * roll back transaction
	 */
	protected void rollback() {
		this.transaction.rollback();
		this.transaction = null;
	}

	/**
	 * @return the current connection manager
	 */
	protected ConnectionManager getConnectionManager() {
		return connectionManager;
	}
	/**
	 * @return the current transaction
	 */
	protected Transaction getTransaction() {
		return transaction;
	}
}
