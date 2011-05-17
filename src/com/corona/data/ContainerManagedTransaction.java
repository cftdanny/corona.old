/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import javax.transaction.UserTransaction;

/**
 * <p>This transaction is used to control data source by  </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ContainerManagedTransaction implements Transaction {

	/**
	 * whether transaction is active
	 */
	private boolean active = false;
	
	/**
	 * whether only allow roll back
	 */
	private boolean rollback = false;

	/**
	 * current user transaction
	 */
	private UserTransaction transaction;
	
	/**
	 * @param transaction current user transaction
	 */
	ContainerManagedTransaction(final UserTransaction transaction) {
		this.transaction = transaction;
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
			this.transaction.begin();
		} catch (Exception e) {
			throw new DataRuntimeException("Fail to start user transaction");
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
			this.transaction.commit();
		} catch (Exception e) {
			throw new DataRuntimeException("Fail to commit change by user transaction");
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
			this.transaction.commit();
		} catch (Exception e) {
			throw new DataRuntimeException("Fail to roll back change by user transaction");
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

		try {
			this.transaction.setRollbackOnly();
		} catch (Exception e) {
			throw new DataRuntimeException("Fail to set roll back only to current transaction");
		}
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
