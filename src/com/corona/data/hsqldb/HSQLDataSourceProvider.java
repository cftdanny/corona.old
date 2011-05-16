/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.hsqldb;

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
public class HSQLDataSourceProvider implements DataSourceProvider {

	/**
	 * the data source family
	 */
	private static final String NAME = "HSQL";
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.DataSourceProvider#getFamily()
	 */
	@Override
	public String getFamily() {
		return NAME;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.DataSourceProvider#create(java.util.Properties)
	 */
	@Override
	public ConnectionManagerFactory create(final Properties properties) throws DataException {
		return new HSQLConnectionManagerFactory(properties);
	}
}
