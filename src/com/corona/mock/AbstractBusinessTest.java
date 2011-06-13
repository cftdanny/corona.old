/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.mock;

import java.sql.Connection;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.corona.context.Key;
import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
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
	 * the connection manager factory
	 */
	private ConnectionManagerFactory connectionManagerFactory;
	
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
	@BeforeMethod public void before() throws Exception {
		super.before();
		
		// restart HSQLDB if required
		this.connectionManagerFactory = this.get(ConnectionManagerFactory.class);
		if (this.isNeedRestart()) {
			this.restart();
		}
		this.connectionManager = this.get(ConnectionManager.class);
	}

	/**
	 * @return whether can restart database server
	 */
	protected boolean isNeedRestart() {
		return true;
	}
	
	/**
	 * restart database if it is HSQLDB
	 * @exception Exception if fail to restart HSQLDB
	 */
	protected void restart() throws Exception {
		
		if ("HSQL".equals(this.connectionManagerFactory.getDataSourceProvider().getFamily())) {
			
			// If current connection manager is not closed, will close it first
			if ((this.connectionManager != null) && (!this.connectionManager.isClosed())) {
				this.connectionManager.close();
			}
			this.connectionManagerFactory.close();
			
			// shutdown HSQLDB, in order to create a blank database
			Connection connection = ((Connection) this.connectionManagerFactory.open().getSource());
			connection.createStatement().execute("SHUTDOWN");
			
			// re-open database, make sure HSQLDB can be connected
			this.connectionManagerFactory.open().close();
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.mock.AbstractComponentTest#after()
	 */
	@Override
	@AfterMethod public void after() throws Exception {
		
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
	 * @return the current connection manager factory
	 */
	protected ConnectionManagerFactory getConnectionManagerFactory() {
		return this.connectionManagerFactory;
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
	
	/**
	 * @return the default database schema
	 */
	protected String getDatabaseSchema() {
		return this.getConfig().getDatabaseSchema();
	}
	
	/**
	 * @param resource the resource
	 * @return the table set
	 * @throws Exception if fail
	 */
	protected TableSet loadTableSet(final String resource) throws Exception {
		return new TableSet(this.getClass(), resource);
	}

	/**
	 * @param resource the resource
	 * @param tableName the table name
	 * @return the table
	 * @throws Exception if fail
	 */
	protected Table loadTable(final String resource, final String tableName) throws Exception {
		return new TableSet(this.getClass(), resource).getTable(tableName);
	}

	/**
	 * @param tableNames the table name
	 * @return the table set
	 * @throws Exception if fail
	 */
	protected TableSet queryTableSet(final String... tableNames) throws Exception {
		return new TableSet(this.connectionManager, this.getDatabaseSchema(), tableNames);
	}

	/**
	 * @param tableName the table name
	 * @return the table set
	 * @throws Exception if fail
	 */
	protected Table queryTable(final String tableName) throws Exception {
		return new Table(this.connectionManager, this.getDatabaseSchema(), tableName);
	}

	/**
	 * <p>Load resource and insert to database, the resource is same as test case class name. </p>
	 * 
	 * @return the table set that is loaded and inserted to database 
	 * @throws Exception if fail to insert resource file to database
	 */
	protected TableSet loadDatabase() throws Exception {
		
		String name = this.getClass().getSimpleName();
		if (this.getClass().getResourceAsStream(name + ".xlsx") != null) {
			return this.load(name + ".xlsx");
		} else if (this.getClass().getResourceAsStream(name + ".xls") != null) {
			return this.load(name + ".xls");
		} else {
			return this.load(name + ".xml");
		}
	}
	
	/**
	 * <p>Load database content from a resource file. </p>
	 * 
	 * @param resource the resource that will load to database
	 * @return the table set that is loaded and inserted to database 
	 * @throws Exception if fail to insert resource file to database
	 */
	protected TableSet load(final String resource) throws Exception {
		TableSet tableset = this.loadTableSet(resource);
		tableset.load(this.connectionManagerFactory, this.getDatabaseSchema());
		return tableset;
	}
}
