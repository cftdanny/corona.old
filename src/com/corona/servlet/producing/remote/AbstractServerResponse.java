/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.OutputStream;

import com.corona.crypto.CypherException;
import com.corona.remote.Constants;
import com.corona.remote.RemoteException;
import com.corona.remote.Server;

/**
 * <p>The helper class for server response </p>
 *
 * @author $Author$
 * @version $Id$
 */
abstract class AbstractServerResponse implements ServerResponse {

	/**
	 * the server
	 */
	private Server server;
	
	/**
	 * @param server the server
	 */
	AbstractServerResponse(final Server server) {
		this.server = server;
	}
	
	/**
	 * @return the server
	 */
	Server getServer() {
		return server;
	}

	/**
	 * @return the action code
	 */
	protected abstract byte getCode();

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.ServerResponse#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {
		
		try {
			output.write(Constants.IDENTIFIER);
			output.write(this.getCode());
		} catch (Exception e) {
			throw new RemoteException("Fail to write identifier and action code to client output stream", e);
		}
	}
	
	/**
	 * @param data the data to be encrypted with server key
	 * @return the encrypted data
	 * @throws RemoteException if fail to encrypt data
	 */
	protected byte[] encryptWithServerKey(final byte[] data) throws RemoteException {
		
		try {
			return this.server.getServerCypher().encrypt(data);
		} catch (CypherException e) {
			throw new RemoteException("Fail to encrpt data to be sent to client output stream with server key");
		}
	}

	/**
	 * @param token the client token
	 * @param data the data to be encrypted with server key
	 * @return the encrypted data
	 * @throws RemoteException if fail to encrypt data
	 */
	protected byte[] encryptWithClientKey(final String token, final byte[] data) throws RemoteException {
		
		try {
			return this.server.getClientCypher(token).encrypt(data);
		} catch (CypherException e) {
			throw new RemoteException("Fail to encrpt data to be sent to client output stream with client key");
		}
	}
}
