/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.mysql;

import java.util.Properties;

import com.corona.data.ConnectionManagerFactory;
import com.corona.data.DataException;
import com.corona.data.DataSourceProvider;

/**
 * <p>This provider is used to create MySQL Server data source provider </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class MySQLDataSourceProvider implements DataSourceProvider {

	/**
	 * the data source family
	 */
	private static final String FAMILY = "MySQL";
	
	/**
	 * the JDBC driver
	 */
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.DataSourceProvider#getFamily()
	 */
	@Override
	public String getFamily() {
		return FAMILY;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.DataSourceProvider#create(java.util.Properties)
	 */
	@Override
	public ConnectionManagerFactory create(final Properties properties) throws DataException {
		
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new DataException("Fail to load HSQLDB JDBC driver [{0}]", DRIVER);
		}
		return new MySQLConnectionManagerFactory(this, properties);
	}
}
