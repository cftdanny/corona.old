/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
import com.corona.data.DataException;
import com.corona.data.DataSourceProvider;
import com.corona.data.EntityMetaDataRepository;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This factory is used to create {@link ConnectionManager}. Normally, it is created by {@DataSourceManager}
 * with data source configuration.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class SQLConnectionManagerFactory implements ConnectionManagerFactory, 
		SQLConnectionManagerCloseListener {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(SQLConnectionManagerFactory.class);
	
	/**
	 * the parent data source provider
	 */
	private DataSourceProvider dataSourceProvider;
	
	/**
	 * the entity configuration manager
	 */
	private EntityMetaDataRepository entityMetaDataManager = new SQLEntityMetaDataManager();
	
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
	 * how many connection manager can be idles;
	 */
	private int maxIdleConnectionManagers = 2;
	
	/**
	 * the cached SQL connection manager
	 */
	private Vector<SQLConnectionManager> connectionManagers = new Vector<SQLConnectionManager>();
	
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
		
		// get how many connection manager can be idle
		try {
			String maxIdles = (String) this.properties.get(DataSourceProvider.SQL_MAX_IDLES);
			this.maxIdleConnectionManagers = Integer.parseInt(maxIdles);
		} catch (Exception e) {
			this.maxIdleConnectionManagers = 2;
		}
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
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManagerFactory#getEntityMetaDataRepository()
	 */
	@Override
	public EntityMetaDataRepository getEntityMetaDataRepository() {
		return this.entityMetaDataManager;
	}

	/**
	 * @return the cached connection manager or <code>null</code> if does not exists
	 */
	protected SQLConnectionManager getCachedConnectionManager() {
		
		SQLConnectionManager connectionManager = null;
		if (this.connectionManagers.size() > 0) {
			try {
				connectionManager = this.connectionManagers.remove(0);
			} catch (Exception e) {
				connectionManager = null;
			}
		}
		return connectionManager;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Closeable#close()
	 */
	@Override
	public void close() {
		
		for (ConnectionManager connectionManager : this.connectionManagers.toArray(new ConnectionManager[0])) {
			try {
				connectionManager.close();
			} catch (Exception e) {
				this.logger.error("Fail to close pooled connection manager, just skip this error", e);
			}
		}
		this.connectionManagers.clear();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.sql.SQLConnectionManagerCloseListener#close(
	 * 	com.corona.data.sql.SQLConnectionManagerCloseEvent
	 * )
	 */
	@Override
	public void close(final SQLConnectionManagerCloseEvent event) {
		
		if ((!event.isCancel()) && (this.connectionManagers.size() < this.maxIdleConnectionManagers)) {
			this.connectionManagers.add(event.getSource());
			event.setCancel(true);
		}
		event.getSource().removeCloseListener(this);
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
