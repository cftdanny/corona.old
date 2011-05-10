/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.Connection;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
import com.corona.data.Dialect;
import com.corona.data.Query;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLConnectionManager implements ConnectionManager {

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getConnectionManagerFactory()
	 */
	@Override
	public ConnectionManagerFactory getConnectionManagerFactory() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getDialect()
	 */
	@Override
	public Dialect getDialect() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getSource()
	 */
	@Override
	public Connection getSource() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#isClosed()
	 */
	@Override
	public boolean isClosed() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#close()
	 */
	@Override
	public void close() {
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
