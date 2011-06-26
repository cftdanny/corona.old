/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import com.corona.crypto.CipherEngine;

/**
 * <p>The helper class of client </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractClient implements Client {

	/**
	 * the configurator
	 */
	private Configurator configurator;
	
	/**
	 * @param configurator the configurator
	 */
	protected AbstractClient(final Configurator configurator) {
		this.configurator = configurator;
	}
	
	/**
	 * @return the client and server configurator
	 */
	public Configurator getConfigurator() {
		return this.configurator;
	}
	
	/**
	 * @return the base URL for server service
	 */
	public String getBaseURL() {
		return this.configurator.getBaseURL();
	}
	
	/**
	 * @return the cipher engine for server
	 */
	public CipherEngine getServerCipher() {
		return this.configurator.getServerCipher();
	}
	
	/**
	 * @return the cipher engine for client
	 */
	public CipherEngine getClientCipher() {
		return this.configurator.getClientCipher();
	}

	/**
	 * @param serviceName the service name
	 * @return the connection for service
	 * @exception RemoteException if fail to create connection
	 */
	protected abstract Connection getConnection(final String serviceName) throws RemoteException;
}
