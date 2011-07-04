/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.IOException;
import java.io.OutputStream;

import com.corona.crypto.CypherException;
import com.corona.remote.Constants;
import com.corona.remote.RemoteException;
import com.corona.remote.Server;

/**
 * <p>This response is used to response client that client has been logged in </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ServerLoggedInResponse extends AbstractServerResponse {

	/**
	 * the token
	 */
	private String token;
	
	/**
	 * @param server the server
	 * @param token the token
	 */
	ServerLoggedInResponse(final Server server, final String token) {
		super(server);
		this.token = token;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.AbstractServerResponse#getCode()
	 */
	@Override
	protected byte getCode() {
		return Constants.RESPONSE.LOGGED_IN;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.AbstractServerResponse#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {
		
		// write assigned token to client output stream
		super.write(output);
		
		// write server library version
		try {
			output.write(Constants.LIBRARY_VESION);
		} catch (IOException e) {
			throw new RemoteException("Fail to write server library version to client output stream", e);
		}
		
		// write assigned to client token
		try {
			this.getServer().getServerCypher().encrypt(this.token.getBytes());
		} catch (CypherException e) {
			throw new RemoteException("Fail to write token assigned to client output stream", e);
		}
	}
}
