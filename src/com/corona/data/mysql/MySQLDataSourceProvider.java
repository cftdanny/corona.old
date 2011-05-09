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
	 * {@inheritDoc}
	 * @see com.corona.data.DataSourceProvider#getFamily()
	 */
	@Override
	public String getFamily() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.DataSourceProvider#create(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ConnectionManagerFactory create(
			final String url, final String username, final String password) throws DataException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.DataSourceProvider#create(java.lang.String, java.util.Properties)
	 */
	@Override
	public ConnectionManagerFactory create(final String url, final Properties properties) throws DataException {
		return null;
	}
}
