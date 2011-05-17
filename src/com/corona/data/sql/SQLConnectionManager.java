/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.Connection;
import java.sql.SQLException;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
import com.corona.data.DataException;
import com.corona.data.DataRuntimeException;
import com.corona.data.Query;
import com.corona.data.ResultHandler;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>The base and helper connection manager for SQL database. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class SQLConnectionManager implements ConnectionManager {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(SQLConnectionManager.class);
	
	/**
	 * the SQL connection manager factory
	 */
	private SQLConnectionManagerFactory connectionManagerFactory;
	
	/**
	 * the opened JDBC connection
	 */
	private Connection connection;
	
	/**
	 * @param connectionManagerFactory the SQL connection manager factory
	 * @exception DataException if fail to create connection manager factory
	 */
	public SQLConnectionManager(final SQLConnectionManagerFactory connectionManagerFactory) throws DataException {
		this.connectionManagerFactory = connectionManagerFactory;
		this.connection = this.connectionManagerFactory.getConnection();
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getConnectionManagerFactory()
	 */
	@Override
	public ConnectionManagerFactory getConnectionManagerFactory() {
		return this.connectionManagerFactory;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getSource()
	 */
	@Override
	public Connection getSource() {
		return this.connection;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#isClosed()
	 */
	@Override
	public boolean isClosed() {
		
		try {
			return this.connection.isClosed();
		} catch (Exception e) {
			this.logger.error("Fail to test whether JDBC connection is closed", e);
			throw new DataRuntimeException("Fail to test whether JDBC connection is closed", e);
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
		} catch (Exception e) {
			this.logger.error("Fail to close opened JDBC connection", e);
			throw new DataRuntimeException("Fail to close opened JDBC connection", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createQuery(java.lang.Class, java.lang.String)
	 */
	@Override
	public <E> Query<E> createQuery(final Class<E> resultClass, final String query) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createQuery(
	 * 	java.lang.Class, java.lang.String, com.corona.data.ResultHandler
	 * )
	 */
	@Override
	public <E> Query<E> createQuery(final Class<E> resultClass, final String query, final ResultHandler<E> handler) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createCommand(java.lang.String)
	 */
	@Override
	public Command createCommand(final String command) {
		
		try {
			return new SQLCommand(this, command);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to create SQL command by [{0}]", e, command);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedCommand(java.lang.Class)
	 */
	@Override
	public Command createNamedCommand(final Class<?> commandClass) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedCommand(java.lang.Class, java.lang.String)
	 */
	@Override
	public Command createNamedCommand(Class<?> commandClass, String name) {
		return null;
	}
}
