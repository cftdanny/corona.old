/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.DataRuntimeException;
import com.corona.data.Query;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>The connection manager for MySQL Server. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class MySQLConnectionManager implements ConnectionManager {

	/**
	 * the MySQL server dialect
	 */
	private MySQLDialect dialect = new MySQLDialect(this);

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(MySQLConnectionManager.class);
	
	/**
	 * the parent connection manager factory
	 */
	private MySQLConnectionManagerFactory connectionManagerFactory;
	
	/**
	 * the opened JDBC connection
	 */
	private Connection connection;
	
	/**
	 * @param connectionManagerFactory the parent connection manager factory
	 * @param connection the opened JDBC connection
	 */
	MySQLConnectionManager(final MySQLConnectionManagerFactory connectionManagerFactory, final Connection connection) {
		this.connectionManagerFactory = connectionManagerFactory;
		this.connection = connection;
	}
	
	/**
	 * @return the opened JDBC connection
	 */
	Connection getConnection() {
		return this.connection;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getConnectionManagerFactory()
	 */
	@Override
	public MySQLConnectionManagerFactory getConnectionManagerFactory() {
		return this.connectionManagerFactory;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getDialect()
	 */
	@Override
	public MySQLDialect getDialect() {
		return this.dialect;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#isClosed()
	 */
	@Override
	public boolean isClosed() {
		
		try {
			
			return this.connection.isClosed();
		} catch (SQLException e) {
			
			this.logger.error("Fail to check whether MySQL JDBC connection is closed");
			throw new DataRuntimeException("Fail to check whether MySQL JDBC connection is closed");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#close()
	 */
	@Override
	public void close() {
		
		try {
			
			this.connection.close();
		} catch (SQLException e) {
			
			this.logger.error("Fail to close MySQL JDBC connection");
			throw new DataRuntimeException("Fail to close MySQL JDBC connection");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createQuery(java.lang.Class, java.lang.String)
	 */
	@Override
	public <T> Query<T> createQuery(Class<? extends T> entityClass, String query) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedQuery(java.lang.Class)
	 */
	@Override
	public <T> Query<T> createNamedQuery(Class<? extends T> entityClass) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createCommand()
	 */
	@Override
	public Command createCommand() {
		return null;
	}
}
