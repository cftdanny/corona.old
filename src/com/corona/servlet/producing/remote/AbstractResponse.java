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
 * <p>The helper of response </p>
 *
 * @author $Author$
 * @version $Id$
 */
abstract class AbstractResponse implements Response {

	/**
	 * the server
	 */
	private Server server;
	
	/**
	 * @return the server
	 */
	public Server getServer() {
		return server;
	}

	/**
	 * @param output the output
	 * @throws RemoteException if fail to write mode and action
	 */
	protected void writeModeAndAction(final OutputStream output) throws RemoteException {
		
		try {
			output.write(Constants.IDENTIFIER);
			output.write(this.getCode());
		} catch (Exception e) {
			throw new RemoteException("Fail to write mode and action to response stream", e);
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
			throw new RemoteException("Fail to encrpt data to be sent to client with server key");
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
			throw new RemoteException("Fail to encrpt data to be sent to client with client key");
		}
	}
}
