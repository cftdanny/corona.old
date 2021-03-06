/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.Connection;
import java.sql.SQLException;

import com.corona.data.DataRuntimeException;
import com.corona.data.Transaction;

/**
 * <p>This transaction is used to control resource for a SQL connection manager </p>
 *
 * @author $Author$
 * @version $Id$
 */
class SQLLocalTransaction implements Transaction {

	/**
	 * whether transaction is active
	 */
	private boolean active = false;
	
	/**
	 * whether only allow roll back
	 */
	private boolean rollback = false;
	
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
		
		if (this.active) {
			throw new DataRuntimeException("Transaction starts already, cana not begin again");
		}
		
		this.rollback = false;
		try {
			this.connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to start database transaction");
		}
		this.active = true;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Transaction#commit()
	 */
	@Override
	public void commit() {

		if (!this.active) {
			throw new DataRuntimeException("Transaction does not start yet, can not commit");
		}
		if (this.rollback) {
			throw new DataRuntimeException("Only roll back transaction is allowed right now");
		}

		try {
			this.connection.commit();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to commit changes to database");
		}
		this.active = false;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Transaction#rollback()
	 */
	@Override
	public void rollback() {
		
		if (!this.active) {
			throw new DataRuntimeException("Transaction does not start yet, can not roll back");
		}

		try {
			this.connection.rollback();
		} catch (SQLException e) {
			
			throw new DataRuntimeException("Fail to roll back changes from database");
		} finally {
			
			try {
				this.connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new DataRuntimeException("Fail to set auto commit to true to connection");
			}
		}
		this.active = false;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Transaction#isActive()
	 */
	@Override
	public boolean isActive() {
		return this.active;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Transaction#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly() {
		this.rollback = true;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Transaction#getRollbackOnly()
	 */
	@Override
	public boolean getRollbackOnly() {
		return this.rollback;
	}
}
