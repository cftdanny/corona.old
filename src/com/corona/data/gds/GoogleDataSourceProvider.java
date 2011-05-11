/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.gds;

import java.util.Properties;

import com.corona.data.ConnectionManagerFactory;
import com.corona.data.DataException;
import com.corona.data.DataSourceProvider;

/**
 * <p>The data source provider for Google App Engine. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class GoogleDataSourceProvider implements DataSourceProvider {

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.DataSourceProvider#getFamily()
	 */
	@Override
	public String getFamily() {
		return "AppEngine";
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.DataSourceProvider#create(java.util.Properties)
	 */
	@Override
	public ConnectionManagerFactory create(final Properties properties) throws DataException {
		return new GoogleConnectionManagerFactory();
	}
}
