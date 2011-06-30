/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import com.corona.crypto.Cypher;

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
	private ClientConfiguration configurator;
	
	/**
	 * @param configurator the configurator
	 */
	protected AbstractClient(final ClientConfiguration configurator) {
		this.configurator = configurator;
	}
	
	/**
	 * @return the client and server configurator
	 */
	public ClientConfiguration getConfigurator() {
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
	public Cypher getServerCipher() {
		return this.configurator.getServerCipher();
	}
	
	/**
	 * @return the cipher engine for client
	 */
	public Cypher getClientCipher() {
		return this.configurator.getClientCipher();
	}

	/**
	 * @return whether client cipher is defined and assigned
	 */
	public boolean hasClientCipher() {
		return this.configurator.getClientCipher() != null;
	}
	
	/**
	 * @param serviceName the service name
	 * @return the connection for service
	 * @exception RemoteException if fail to create connection
	 */
	protected abstract Connection getConnection(final String serviceName) throws RemoteException;
}
