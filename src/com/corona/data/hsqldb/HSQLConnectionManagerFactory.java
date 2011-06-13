/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.hsqldb;

import java.util.Properties;

import com.corona.data.ConnectionManager;
import com.corona.data.DataException;
import com.corona.data.DataSourceProvider;
import com.corona.data.sql.SQLConnectionManager;
import com.corona.data.sql.SQLConnectionManagerFactory;

/**
 * <p>The connection manager factory for MySQL Server. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class HSQLConnectionManagerFactory extends SQLConnectionManagerFactory {
	
	/**
	 * @param dataSourceProvider the data source provider that creates this connection manager factory
	 * @param properties the data source configuration
	 */
	HSQLConnectionManagerFactory(final DataSourceProvider dataSourceProvider, final Properties properties) {
		super(dataSourceProvider, properties);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManagerFactory#open()
	 */
	@Override
	public ConnectionManager open() throws DataException {
		
		SQLConnectionManager connectionManager = this.getCachedConnectionManager();
		if ((connectionManager == null) || connectionManager.isClosed()) {
			connectionManager = new HSQLConnectionManager(this);
		}
		connectionManager.addCloseListener(this);
		return connectionManager;
	}
}
