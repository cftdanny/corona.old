/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.gds;

import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
import com.corona.data.DataException;
import com.corona.data.DataSourceProvider;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

/**
 * <p>The connection manager factory for Google Datastore Service. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class GoogleConnectionManagerFactory implements ConnectionManagerFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManagerFactory#getDataSourceProvider()
	 */
	@Override
	public DataSourceProvider getDataSourceProvider() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManagerFactory#open()
	 */
	@Override
	public ConnectionManager open() throws DataException {
		return new GoogleConnectionManager(this, DatastoreServiceFactory.getDatastoreService());
	}
}
