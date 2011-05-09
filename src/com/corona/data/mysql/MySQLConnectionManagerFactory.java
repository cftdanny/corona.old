/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
import com.corona.data.DataException;
import com.corona.data.DataSourceProvider;
import com.corona.data.Dialect;

/**
 * <p>The connection manager factory for MySQL Server. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class MySQLConnectionManagerFactory implements ConnectionManagerFactory {

	/**
	 * the MySQL server dialect
	 */
	private MySQLDialect dialect = new MySQLDialect();
	
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
	 * @param properties the data source configuration
	 */
	MySQLConnectionManagerFactory(final Properties properties) {
		
		// save data source configuration
		this.properties = properties;

		// find defined variable from properties
		this.jndi = this.properties.getProperty(DataSourceProvider.JNDI);
		
		this.url = this.properties.getProperty(DataSourceProvider.URL);
		this.user = this.properties.getProperty(DataSourceProvider.USER);
		this.password = this.properties.getProperty(DataSourceProvider.PASSWORD);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManagerFactory#getDialect()
	 */
	@Override
	public Dialect getDialect() {
		return this.dialect;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManagerFactory#open()
	 */
	@Override
	public ConnectionManager open() throws DataException {
		
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
		
		return new MySQLConnectionManager(this, connection);
	}
}
