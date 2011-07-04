/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.IOException;
import java.io.InputStream;

import com.corona.remote.AbstractResponse;
import com.corona.remote.Constants;
import com.corona.remote.RemoteException;

/**
 * <p>User has logged in into server </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ClientLoggedInResponse extends AbstractResponse {

	/**
	 * the server library version
	 */
	private byte serverLibraryVersion;
	
	/**
	 * the token that feedback from server
	 */
	private String token;
	
	/**
	 * @param client the client
	 */
	ClientLoggedInResponse(final Client client) {
		super(client);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientResponse#getCode()
	 */
	@Override
	public int getCode() {
		return Constants.RESPONSE.LOGGED_IN;
	}
	
	/**
	 * @return the server library version
	 */
	public byte getServerLibraryVersion() {
		return serverLibraryVersion;
	}
	
	/**
	 * @return the token that feedback from server
	 */
	public String getToken() {
		return token;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientResponse#read(java.io.InputStream)
	 */
	@Override
	public void read(final InputStream input) throws RemoteException {
		
		// read first byte from server response, it should server framework version
		int b;
		try {
			b = input.read();
			if (b == -1) {
				throw new RemoteException("Need more data from server, but it reaches end of stream");
			}
		} catch (IOException e) {
			throw new RemoteException("Fail to read data that is sent from server response", e);
		}
		this.serverLibraryVersion = (byte) b;
		
		// read token from server response
		this.token = new String(this.decryptWithServerKey(this.getBytes(input)));
	}
}
