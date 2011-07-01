/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.corona.crypto.CypherException;

/**
 * <p>The helper response </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractResponse implements Response {

	/**
	 * the client
	 */
	private AbstractClient client;
	
	/**
	 * @param client the client
	 */
	protected AbstractResponse(final AbstractClient client) {
		this.client = client;
	}
	
	/**
	 * @return the client
	 */
	protected AbstractClient getClient() {
		return client;
	}

	/**
	 * @param input the input stream from server
	 * @param length how many bytes will read
	 * @return the bytes read
	 * @throws RemoteException if fail to read data
	 */
	protected byte[] getBytes(final InputStream input, final int length) throws RemoteException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			for (int i =  0; i < length; i++) {
				
				int b = input.read();
				if (b == -1) {
					throw new RemoteException(
							"Need [{0}] bytes from server, but only read [{1}] bytes right now", length, i
					);
				}
				baos.write(b);
			}
		} catch (IOException e) {
			throw new RemoteException("Fail to read data sent from server", e);
		}
		return baos.toByteArray();
	}

	/**
	 * @param input the input stream from server
	 * @return the bytes read
	 * @throws RemoteException if fail to read data
	 */
	protected byte[] getBytes(final InputStream input) throws RemoteException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			for (int b = input.read(); b != -1; b = input.read()) {
				baos.write(b);
			}
		} catch (IOException e) {
			throw new RemoteException("Fail to read data sent from server", e);
		}
		return baos.toByteArray();
	}
	
	/**
	 * @param data the encrypted data with server key
	 * @return the decrypted data 
	 * @throws RemoteException if fail to decrypt data
	 */
	protected byte[] decryptWithServerKey(final byte[] data) throws RemoteException {
		
		try {
			return this.client.getServerCypher().decrypt(data);
		} catch (CypherException e) {
			throw new RemoteException("Fail to decrypt data from server by server decryption key");
		}
	}
	
	/**
	 * @param data the encrypted data with client key
	 * @return the decrypted data 
	 * @throws RemoteException if fail to decrypt data
	 */
	protected byte[] decryptWithClientKey(final byte[] data) throws RemoteException {
		
		try {
			return this.client.getClientCypher().decrypt(data);
		} catch (CypherException e) {
			throw new RemoteException("Fail to decrypt data from server with client decryption key");
		}
	}
}
