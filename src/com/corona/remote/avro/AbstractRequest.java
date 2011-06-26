/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import java.io.IOException;
import java.io.OutputStream;

import com.corona.crypto.CipherException;
import com.corona.remote.Constants;
import com.corona.remote.RemoteException;
import com.corona.remote.Request;

/**
 * <p>The helper request for Apache Avro </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractRequest implements Request {

	/**
	 * the client
	 */
	private AvroClient client;
	
	/**
	 * @param client the client
	 */
	AbstractRequest(final AvroClient client) {
		this.client = client;
	}
	
	/**
	 * @return the client
	 */
	protected AvroClient getClient() {
		return client;
	}
	
	/**
	 * @param output the output stream that is used to send data to server
	 * @throws RemoteException if fail to send data to server
	 */
	protected void sendModeAndAction(final OutputStream output) throws RemoteException {
		
		try {
			if (this.client.isProductionMode()) {
				output.write(Constants.PRODUCTION_MODE);
			} else {
				output.write(Constants.DEVELOPMENT_MODE);
			}
			output.write(this.getCode());
		} catch (IOException e) {
			throw new RemoteException("Fail to send stream data to remote server", e);
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
			throw new RemoteException("Fail to encrpt data with server key");
		}
	}
}
