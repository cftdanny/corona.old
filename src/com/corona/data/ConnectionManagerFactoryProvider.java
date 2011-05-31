/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.Provider;
import com.corona.context.annotation.Application;
import com.corona.context.annotation.Inject;

/**
 * <p>This provider is used to get configured connection manager factory from context. </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Application
public class ConnectionManagerFactoryProvider implements Provider<ConnectionManagerFactory> {

	/**
	 * the current context manager
	 */
	@Inject private ContextManager contextManager;

	/**
	 * the data source component name
	 */
	private String name;
	
	/**
	 * @param name the data source component name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Provider#get()
	 */
	@Override
	public ConnectionManagerFactory get() {
		
		DataSourceManagerProvider provider = this.contextManager.get(DataSourceManagerProvider.class, this.name);
		if (provider == null) {
			throw new ConfigurationException("Data source supporting components are not configured yet");
		}
		return provider.getConnectionManagerFactory();
	}
}
