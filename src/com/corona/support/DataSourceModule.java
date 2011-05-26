/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.support;

import com.corona.context.AbstractModule;
import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;

/**
 * <p>This module is used to register database support component (ConnectionManager, ConnectionManagerFactory) to
 * context manager factory. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DataSourceModule extends AbstractModule {

	/**
	 * the component name
	 */
	private String name;
	
	/**
	 * default constructor with empty component name
	 */
	public DataSourceModule() {
		this(null);
	}
	
	/**
	 * @param name the component name
	 */
	public DataSourceModule(final String name) {
		this.name = name;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		// register components (data source, connection manager, connection manager factory)
		this.bind(DataSourceManagerProvider.class).to(DataSourceManagerProvider.class).as(this.name);
		this.bindProvider(ConnectionManagerFactory.class).to(ConnectionManagerFactoryProvider.class).as(this.name);
		this.bindProvider(ConnectionManager.class).to(ConnectionManagerProvider.class).as(this.name);
		
		// bind component name to connection manager provider and connection manager factory provider
		this.bindSetting(ConnectionManagerFactory.class).as(this.name).to("name").with(this.name);
		this.bindSetting(ConnectionManager.class).as(this.name).to("name").with(this.name);
	}
}
