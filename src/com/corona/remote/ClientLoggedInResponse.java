/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.IOException;
import java.io.InputStream;

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
		try {
			int b = input.read();
			if (b == -1) {
				throw new RemoteException("Should read server library version but client input stream is empty");
			}
			this.serverLibraryVersion = (byte) b;
		} catch (IOException e) {
			throw new RemoteException("Fail to read server library version from client input stream", e);
		}
		
		// read token from server response
		byte[] data = this.getBytes(input);
		this.token = new String(this.decryptWithServerKey(data));
	}
}
