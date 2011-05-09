/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;

/**
 * <p>The data source manager is used to create {@link ConnectionManagerFactory} by database family and 
 * connection URL. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DataSourceManager {

	/**
	 * all database driver providers
	 */
	private Map<String, DataSourceProvider> providers = null;
	
	/**
	 * load all data source provider by service loader
	 */
	private void loadDataSourceProviders() {
		
		Map<String, DataSourceProvider> loading = new HashMap<String, DataSourceProvider>();
		for (DataSourceProvider provider : ServiceLoader.load(DataSourceProvider.class)) {
			loading.put(provider.getFamily(), provider);
		}
		
		this.providers = loading;
	}
	
	/**
	 * <p>Create connection manager factory by database family and JDBC connection URL. </p>
	 * 
	 * @param family the database family, for example, MySQL, Oracle, DB2, etc
	 * @param url a database URL of the form jdbc:subprotocol:subname
	 * @return the connection manager factory
	 * @exception DataException if fail to create {@link ConnectionManagerFactory}
	 */
	public ConnectionManagerFactory create(final String family, final String url) throws DataException {
		return this.create(family, url, null, null);
	}
	
	/**
	 * <p>Create connection manager factory by database family and JDBC connection URL. </p>
	 * 
	 * @param family the database family, for example, MySQL, Oracle, DB2, etc
	 * @param url a database URL of the form jdbc:subprotocol:subname
	 * @param username the user name to log in database server
	 * @param password the password for user
	 * @return the connection manager factory
	 * @exception DataException if fail to create {@link ConnectionManagerFactory}
	 */
	public ConnectionManagerFactory create(
			final String family, final String url, final String username, final String password
	) throws DataException {
		
		// load all data source providers that are registered by service loader
		if (this.providers == null) {
			this.loadDataSourceProviders();
		}
		
		// find data source provider by database family
		DataSourceProvider provider = this.providers.get(family);
		if (provider == null) {
			throw new DataException("Database family [{0}] does not exist", family);
		}
		
		// create data source provider by registered family
		return provider.create(url, username, password);
	}
	
	/**
	 * <p>Create connection manager factory by database family and JDBC connection URL. </p>
	 * 
	 * @param family the database family, for example, MySQL, Oracle, DB2, etc
	 * @param url a database URL of the form jdbc:subprotocol:subname
	 * @param properties a list of arbitrary string tag/value pairs as connection arguments
	 * @return the connection manager factory
	 * @exception DataException if fail to create {@link ConnectionManagerFactory}
	 */
	public ConnectionManagerFactory create(
			final String family, final String url, final Properties properties) throws DataException {
		
		// load all data source providers that are registered by service loader
		if (this.providers == null) {
			this.loadDataSourceProviders();
		}
		
		// find data source provider by database family
		DataSourceProvider provider = this.providers.get(family);
		if (provider == null) {
			throw new DataException("Database family [{0}] does not exist", family);
		}
		
		// create data source provider by registered family
		return provider.create(url, properties);
	}
}
