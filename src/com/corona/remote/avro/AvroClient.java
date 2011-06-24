/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import java.util.Map;

import com.corona.remote.Client;
import com.corona.remote.ClientFactory;
import com.corona.remote.RemoteException;

/**
 * <p>The implementation of client by Apache Avro </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AvroClient implements Client {

	/**
	 * the server key
	 */
	private byte[] serverKey;
	
	/**
	 * the server version
	 */
	private byte serverVersion;
	
	/**
	 * the client key
	 */
	private byte[] clientKey;

	/**
	 * the client version
	 */
	private byte clientVersion = 10;

	/**
	 * the base request URL
	 */
	private String baseUrl;
	
	/**
	 * the token
	 */
	private String token;
	
	/**
	 * @param config the configuration
	 * @exception RemoteException if fail to create client
	 */
	AvroClient(final Map<String, ?> config) throws RemoteException {
		
		this.serverKey = (byte[]) config.get(ClientFactory.SERVER_KEY);
		if (this.serverKey == null) {
			throw new RemoteException("Server key is not installed in order to create client");
		}
		
		this.clientKey = (byte[]) config.get(ClientFactory.CLIENT_KEY);
		if (this.clientKey == null) {
			throw new RemoteException("Client key is not installed in order to create client");
		}
		
		this.baseUrl = (String) config.get(ClientFactory.SERVER_KEY);
		if (this.baseUrl == null) {
			throw new RemoteException("Base request URL must be set in order to create client");
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Client#login(java.lang.String, java.lang.String)
	 */
	@Override
	public void login(final String name, final String password) throws RemoteException {
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Client#logout()
	 */
	@Override
	public void logout() throws RemoteException {
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Client#execute(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object execute(final String name, final Object argument) {
		return null;
	}
}
