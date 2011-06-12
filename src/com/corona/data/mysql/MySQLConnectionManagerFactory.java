/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.mysql;

import java.util.Properties;

import com.corona.data.ConnectionManager;
import com.corona.data.DataException;
import com.corona.data.DataSourceProvider;
import com.corona.data.sql.SQLConnectionManagerFactory;

/**
 * <p>The connection manager factory for MySQL Server. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class MySQLConnectionManagerFactory extends SQLConnectionManagerFactory {
	
	/**
	 * @param dataSourceProvider the data source provider that create this connection manager factory
	 * @param properties the data source configuration
	 */
	MySQLConnectionManagerFactory(final DataSourceProvider dataSourceProvider, final Properties properties) {
		super(dataSourceProvider, properties);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManagerFactory#open()
	 */
	@Override
	public ConnectionManager open() throws DataException {
		
		ConnectionManager connectionManager = this.getCachedConnectionManager();
		if ((connectionManager != null) && (!connectionManager.isClosed())) {
			return connectionManager;
		} else {
			return new MySQLConnectionManager(this);
		}
	}
}
