/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import com.corona.context.AbstractModule;

/**
 * <p>This module is used to register database support component (ConnectionManager, ConnectionManagerFactory) to
 * context manager factory. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DataSourceSupportModule extends AbstractModule {

	/**
	 * the component name
	 */
	private String name;
	
	/**
	 * default constructor with empty component name
	 */
	public DataSourceSupportModule() {
		this(null);
	}
	
	/**
	 * @param name the component name
	 */
	public DataSourceSupportModule(final String name) {
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
		this.bindConfiguration(ConnectionManagerFactory.class).as(this.name).property("name").value(this.name);
		this.bindConfiguration(ConnectionManager.class).as(this.name).property("name").value(this.name);
	}
}
