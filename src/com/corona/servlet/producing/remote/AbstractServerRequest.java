/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.corona.crypto.CypherException;
import com.corona.remote.RemoteException;
import com.corona.remote.Server;

/**
 * <p>the helper class for server request </p>
 *
 * @author $Author$
 * @version $Id$
 */
abstract class AbstractServerRequest implements ServerRequest {

	/**
	 * the server
	 */
	private Server server;
	
	/**
	 * @param server the server
	 */
	AbstractServerRequest(final Server server) {
		this.server = server;
	}

	/**
	 * @return the server
	 */
	protected Server getServer() {
		return server;
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
							"Need [{0}] bytes from client input stream, but only can read [{1}] bytes", length, i
					);
				}
				baos.write(b);
			}
		} catch (IOException e) {
			throw new RemoteException("Fail to read data from client input stream", e);
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
			throw new RemoteException("Fail to read data from client input stream", e);
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
			return this.server.getServerCypher().decrypt(data);
		} catch (CypherException e) {
			throw new RemoteException("Fail to decrypt data from client input stream by server key", e);
		}
	}
}
