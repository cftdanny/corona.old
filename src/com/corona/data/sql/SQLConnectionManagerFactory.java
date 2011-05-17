/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.corona.data.ConnectionManagerFactory;
import com.corona.data.DataException;
import com.corona.data.DataSourceProvider;

/**
 * <p>This factory is used to create {@link ConnectionManager}. Normally, it is created by {@DataSourceManager}
 * with data source configuration.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class SQLConnectionManagerFactory implements ConnectionManagerFactory {

	/**
	 * the parent data source provider
	 */
	private DataSourceProvider dataSourceProvider;
	
	/**
	 * the data source configuration
	 */
	private Properties properties;
	
	/**
	 * the data source JNDI
	 */
	private String jndi = null;
	
	/**
	 * the database JDBC URL
	 */
	private String url;
	
	/**
	 * the user of database
	 */
	private String user;
	
	/**
	 * the password for user
	 */
	private String password;
	
	/**
	 * @param dataSourceProvider the parent data source provider
	 * @param properties the data source configuration
	 */
	public SQLConnectionManagerFactory(final DataSourceProvider dataSourceProvider, final Properties properties) {
		
		// save data source configuration
		this.dataSourceProvider = dataSourceProvider;
		this.properties = properties;

		// find defined variable from properties
		this.jndi = this.properties.getProperty(DataSourceProvider.JNDI);
		
		this.url = this.properties.getProperty(DataSourceProvider.URL);
		this.user = this.properties.getProperty(DataSourceProvider.USER);
		this.password = this.properties.getProperty(DataSourceProvider.PASSWORD);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManagerFactory#getDataSourceProvider()
	 */
	@Override
	public DataSourceProvider getDataSourceProvider() {
		return this.dataSourceProvider;
	}

	/**
	 * @return the opened JDBC connection
	 * @throws DataException if fail to open JDBC connection
	 */
	protected Connection getConnection() throws DataException {
		
		Connection connection = null;
		if (this.jndi != null) {
			
			try {
				Context context = new InitialContext();
				DataSource datasource = (DataSource) context.lookup(this.jndi);
				connection = datasource.getConnection();
			} catch (Exception e) {
				throw new DataException("Fail to MySQL Server connection by JNDI [{0}]", e, this.jndi);
			}
		} else {
			
			try {
				if (this.user != null) {
					connection = DriverManager.getConnection(url, user, password);
				} else {
					connection = DriverManager.getConnection(url, properties);
				}
			} catch (Exception e) {
				throw new DataException("Fail to MySQL Server connection by URL [{0}]", e, this.url);
			}
		}
		return connection;
	}
}
