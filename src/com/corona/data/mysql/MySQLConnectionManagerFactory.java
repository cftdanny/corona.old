/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.mysql;

import java.util.Properties;

import com.corona.data.ConnectionManager;
import com.corona.data.DataException;
import com.corona.data.sql.SQLConnectionManagerFactory;

/**
 * <p>The connection manager factory for MySQL Server. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class MySQLConnectionManagerFactory extends SQLConnectionManagerFactory {
	
	/**
	 * @param properties the data source configuration
	 */
	MySQLConnectionManagerFactory(final Properties properties) {
		super(properties);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManagerFactory#open()
	 */
	@Override
	public ConnectionManager open() throws DataException {
		return new MySQLConnectionManager(this);
	}
}
