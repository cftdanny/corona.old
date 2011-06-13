/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.hsqldb;

import com.corona.data.DataException;
import com.corona.data.sql.SQLConnectionManager;

/**
 * <p>The connection manager is a MySQL Server connection manager. It uses JDBC connection to manage data in
 * MySQL Server. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class HSQLConnectionManager extends SQLConnectionManager {

	/**
	 * the MySQL server dialect
	 */
	private HSQLDialect dialect = new HSQLDialect(this);
	
	/**
	 * @param connectionManagerFactory the parent connection manager factory
	 * @exception DataException if fail to create connection manager factory
	 */
	HSQLConnectionManager(final HSQLConnectionManagerFactory connectionManagerFactory) throws DataException {
		super(connectionManagerFactory);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getDialect()
	 */
	public HSQLDialect getDialect() {
		return this.dialect;
	}
}
