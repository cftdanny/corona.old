/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.Provider;
import com.corona.context.annotation.Context;
import com.corona.context.annotation.Inject;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This provider is used to get configured connection manager from context </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Context
public class ConnectionManagerProvider implements Provider<ConnectionManager> {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(ConnectionManagerProvider.class);
	
	/**
	 * the current context manager
	 */
	@Inject private ContextManager contextManager;
	
	/**
	 * the component name
	 */
	private String name;
	
	/**
	 * @param name the component name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Provider#get()
	 */
	@Override
	public ConnectionManager get() {
		
		DataSourceManagerProvider support = this.contextManager.get(DataSourceManagerProvider.class, name);
		if (support == null) {
			this.logger.error("Data source supporting components are not configured yet");
			throw new ConfigurationException("Data source supporting components are not configured yet");
		}
		
		try {
			return support.getConnectionManager();
		} catch (Exception e) {
			this.logger.error("Fail to open data source", e);
			return null;
		}
	}
}
