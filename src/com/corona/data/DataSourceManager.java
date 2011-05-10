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
	 * <p>Create connection manager factory by database family and JDBC connection URL. </p>
	 * 
	 * @param family the database family, for example, MySQL, Oracle, DB2, etc
	 * @param jndi the JNDI data source name
	 * @return the new {@link ConnectionManagerFactory}
	 * @exception DataException if fail to create {@link ConnectionManagerFactory}
	 */
	public ConnectionManagerFactory create(final String family, final String jndi) throws DataException {
		
		// put the name of JNDI data source to configuration properties
		Properties properties = new Properties();
		properties.put(DataSourceProvider.JNDI, jndi);
		
		// create connection manager factory by data source configuration
		return this.create(family, properties);
	}
	
	/**
	 * <p>Create connection manager factory by database family and JDBC connection URL. </p>
	 * 
	 * @param family the database family, for example, MySQL, Oracle, DB2, etc
	 * @param url a database URL of the form jdbc:subprotocol:subname
	 * @param user the user name to log in database server
	 * @param password the password for user
	 * @return the connection manager factory
	 * @exception DataException if fail to create {@link ConnectionManagerFactory}
	 */
	public ConnectionManagerFactory create(
			final String family, final String url, final String user, final String password
	) throws DataException {
		
		// put JDBC connection URL, database user and password to configuration properties
		Properties properties = new Properties();
		
		properties.put(DataSourceProvider.URL, url);
		properties.put(DataSourceProvider.USER, user);
		properties.put(DataSourceProvider.PASSWORD, password);

		// create connection manager factory by data source configuration
		return this.create(family, properties);
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
		
		// store URL to data source configuration properties
		properties.put(DataSourceProvider.URL, url);
		
		// create data source provider by registered family
		return this.create(family, properties);
	}

	/**
	 * <p>Create connection manager factory by database family and JDBC connection URL. </p>
	 * 
	 * @param family the database family, for example, MySQL, Oracle, DB2, etc
	 * @param properties a list of arbitrary string tag/value pairs as connection arguments
	 * @return the connection manager factory
	 * @exception DataException if fail to create {@link ConnectionManagerFactory}
	 */
	public ConnectionManagerFactory create(final String family, final Properties properties) throws DataException {

		if (this.providers == null) {
			Map<String, DataSourceProvider> loading = new HashMap<String, DataSourceProvider>();
			for (DataSourceProvider provider : ServiceLoader.load(DataSourceProvider.class)) {
				loading.put(provider.getFamily(), provider);
			}
			this.providers = loading;
		}

		// find data source provider by database family
		DataSourceProvider provider = this.providers.get(family);
		if (provider == null) {
			throw new DataException("Database family [{0}] does not exist", family);
		}
		
		// create data source provider by registered family
		return provider.create(properties);
	}
}
