/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.Connection;

import com.corona.data.Transaction;

/**
 * <p>This transaction is used to control resource for a SQL connection manager </p>
 *
 * @author $Author$
 * @version $Id$
 */
class SQLLocalTransaction implements Transaction {

	/**
	 * the JDBC connection
	 */
	private Connection connection;
	
	/**
	 * @param connectionManager the SQL connection manager
	 */
	SQLLocalTransaction(final SQLConnectionManager connectionManager) {
		this.connection = connectionManager.getSource();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Transaction#begin()
	 */
	@Override
	public void begin() {
		this.connectionManager.getSource()
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Transaction#commit()
	 */
	@Override
	public void commit() {
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Transaction#rollback()
	 */
	@Override
	public void rollback() {
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Transaction#isActive()
	 */
	@Override
	public boolean isActive() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Transaction#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly() {
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Transaction#getRollbackOnly()
	 */
	@Override
	public boolean getRollbackOnly() {
		return false;
	}
}
