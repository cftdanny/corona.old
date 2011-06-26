/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.corona.crypto.CipherException;
import com.corona.remote.RemoteException;
import com.corona.remote.Response;

/**
 * <p>The helper class of response </p>
 *
 * @author $Author$
 * @version $Id$
 */
abstract class AbstractResponse implements Response {

	/**
	 * the client
	 */
	private AvroClient client;
	
	/**
	 * @param client the client
	 */
	AbstractResponse(final AvroClient client) {
		this.client = client;
	}
	
	/**
	 * @return the client
	 */
	protected AvroClient getClient() {
		return client;
	}

	/**
	 * @param input the input stream from server response
	 * @return the bytes in array
	 * @throws RemoteException if fail to read from server response
	 */
	protected byte[] getBytes(final InputStream input) throws RemoteException {
		
		List<Byte> list = new ArrayList<Byte>();
		try {
			for (int b = input.read(); b != -1; b = input.read()) {
				list.add((byte) b);
			}
		} catch (IOException e) {
			throw new RemoteException("Fail to read data that is sent from server response", e);
		}
		
		byte[] array = new byte[list.size()];
		for (int i = 0, count = list.size(); i < count; i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	/**
	 * @param data the encrypted data with server key
	 * @return the decrypted data 
	 * @throws RemoteException if fail to decrypt data
	 */
	protected byte[] decryptWithServerKey(final byte[] data) throws RemoteException {
		
		try {
			return this.client.getServerCipher().decrypt(data);
		} catch (CipherException e) {
			throw new RemoteException("Fail to decrypt data from response with server key");
		}
	}
}
