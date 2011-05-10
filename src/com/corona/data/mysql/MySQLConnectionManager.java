/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.mysql;

import java.sql.Connection;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.Query;

/**
 * <p>The connection manager for MySQL Server. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class MySQLConnectionManager implements ConnectionManager {

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
