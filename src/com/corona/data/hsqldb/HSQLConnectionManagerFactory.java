/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.hsqldb;

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
class HSQLConnectionManagerFactory extends SQLConnectionManagerFactory {
	
	/**
	 * @param properties the data source configuration
	 */
	HSQLConnectionManagerFactory(final Properties properties) {
		super(properties);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManagerFactory#open()
	 */
	@Override
	public ConnectionManager open() throws DataException {
		return new HSQLConnectionManager(this);
	}
}
