/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.IOException;
import java.io.OutputStream;

import com.corona.crypto.CipherException;

/**
 * <p>The helper request </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractRequest implements Request {

	/**
	 * the client
	 */
	private AbstractClient client;
	
	/**
	 * @param client the client
	 */
	protected AbstractRequest(final AbstractClient client) {
		this.client = client;
	}
	
	/**
	 * @return the client
	 */
	protected AbstractClient getClient() {
		return client;
	}
	
	/**
	 * @param output the output stream that is used to send data to server
	 * @throws RemoteException if fail to send data to server
	 */
	protected void sendModeAndAction(final OutputStream output) throws RemoteException {
		
		try {
			if (this.client.hasClientCipher()) {
				output.write(Constants.PRODUCTION_MODE);
			} else {
				output.write(Constants.DEVELOPMENT_MODE);
			}
			output.write(this.getCode());
		} catch (IOException e) {
			throw new RemoteException("Fail to send data mode and action code to server", e);
		}
	}
	
	/**
	 * @param data the data to be encrypted with server key
	 * @return the encrypted data
	 * @throws RemoteException if fail to encrypt data
	 */
	protected byte[] encryptWithServerKey(final byte[] data) throws RemoteException {
		
		try {
			return this.client.getServerCipher().encrypt(data);
		} catch (CipherException e) {
			throw new RemoteException("Fail to encrpt data to be sent to server with server key");
		}
	}

	/**
	 * @param data the data to be encrypted with client key
	 * @return the encrypted data
	 * @throws RemoteException if fail to encrypt data
	 */
	protected byte[] encryptWithClientKey(final byte[] data) throws RemoteException {
		
		try {
			return this.client.getClientCipher().encrypt(data);
		} catch (CipherException e) {
			throw new RemoteException("Fail to encrpt data to be sent to server with client key");
		}
	}
}
