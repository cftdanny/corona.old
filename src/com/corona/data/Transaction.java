/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>The transaction is used to control resource transactions on data source. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Transaction {

	/**
	 * Start a resource transaction on data source
	 */
	void begin();
	
	/**
	 * Commit the current transaction, writing any flushed changes to the data source
	 */
	void commit();
	
	/**
	 * Roll back the current transaction
	 */
	void rollback();
	
	/**
	 * <p>Indicate whether a transaction is in progress
	 * </p>
	 * 
	 * @return true if a transaction is in progress
	 */
	boolean isActive();
	
	/**
	 * <p>Mark the current transaction so that the only possible outcome of the transaction is for 
	 * the transaction to be rolled back
	 * </p>
	 */
	void setRollbackOnly();
	
	/**
	 * <p>Determine whether the current transaction has been marked for rollback </p>
	 * 
	 * @return rue if the current transaction has been marked for rollback
	 */
	boolean getRollbackOnly();
}
