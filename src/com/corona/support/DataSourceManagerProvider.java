/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.support;

import java.util.Properties;

import com.corona.context.annotation.Application;
import com.corona.context.annotation.Create;
import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
import com.corona.data.DataException;
import com.corona.data.DataSourceManager;

/**
 * <p>This component is used to create connection manager factory by configuration. </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Application
public class DataSourceManagerProvider {

	/**
	 * the data source family
	 */
	private String family;
	
	/**
	 * the data source setting
	 */
	private Properties settings;
	
	/**
	 * the connection manager
	 */
	private ConnectionManagerFactory connectionManagerFactory;

	/**
	 * @param family the data source family to set
	 */
	public void setFamily(final String family) {
		this.family = family;
	}
	
	/**
	 * @param settings the data source settings to set
	 */
	public void setSettings(final Properties settings) {
		this.settings = settings;
	}

	/**
	 * @throws DataException if fail to create connection manager factory
	 */
	@Create public void init() throws DataException {
		this.connectionManagerFactory = new DataSourceManager().create(this.family, this.settings);
	}
	
	/**
	 * @return the connection manager factory
	 */
	public ConnectionManagerFactory getConnectionManagerFactory() {
		return this.connectionManagerFactory;
	}
	
	/**
	 * @return the opened connection manager
	 * @throws DataException if fail to open connection manager
	 */
	public ConnectionManager getConnectionManager() throws DataException {
		return this.connectionManagerFactory.open();
	}
}
