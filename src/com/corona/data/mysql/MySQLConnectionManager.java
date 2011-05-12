/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.mysql;

import com.corona.data.DataException;
import com.corona.data.sql.SQLConnectionManager;

/**
 * <p>The connection manager is a MySQL Server connection manager. It uses JDBC connection to manage data in
 * MySQL Server. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class MySQLConnectionManager extends SQLConnectionManager {

	/**
	 * the MySQL server dialect
	 */
	private MySQLDialect dialect = new MySQLDialect(this);
	
	/**
	 * @param connectionManagerFactory the parent connection manager factory
	 * @exception DataException if fail to create connection manager factory
	 */
	MySQLConnectionManager(final MySQLConnectionManagerFactory connectionManagerFactory) throws DataException {
		super(connectionManagerFactory);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getDialect()
	 */
	public MySQLDialect getDialect() {
		return this.dialect;
	}
}
