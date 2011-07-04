/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.IOException;
import java.io.OutputStream;

import com.corona.crypto.CypherException;

/**
 * <p>The helper class of request </p>
 *
 * @author $Author$
 * @version $Id$
 */
abstract class AbstractRequest implements ClientRequest {

	/**
	 * the client
	 */
	private Client client;
	
	/**
	 * @param client the client
	 */
	protected AbstractRequest(final Client client) {
		this.client = client;
	}
	
	/**
	 * @return the client
	 */
	protected Client getClient() {
		return client;
	}

	/**
	 * @param data the data to be encrypted with server key
	 * @return the encrypted data
	 * @throws RemoteException if fail to encrypt data
	 */
	protected byte[] encryptWithServerKey(final byte[] data) throws RemoteException {
		
		try {
			return this.client.getConfiguration().getServerCypher().encrypt(data);
		} catch (CypherException e) {
			throw new RemoteException("Fail to encrpt data to client output stream with server key");
		}
	}

	/**
	 * @param data the data to be encrypted with client key
	 * @return the encrypted data
	 * @throws RemoteException if fail to encrypt data
	 */
	protected byte[] encryptWithClientKey(final byte[] data) throws RemoteException {
		
		try {
			return this.client.getConfiguration().getClientCypher().encrypt(data);
		} catch (CypherException e) {
			throw new RemoteException("Fail to encrpt data to client output stream with client key");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientRequest#write(com.corona.remote.Connection)
	 */
	@Override
	public void write(final Connection connection) throws RemoteException {
		
		OutputStream output = connection.getOutputStream();
		this.write(output);
		try {
			try {
				output.write(Constants.IDENTIFIER);
				output.write(this.getCode());
			} catch (IOException e) {
				throw new RemoteException("Fail to write identifier and action code to client output stream", e);
			}
			this.write(output);
		} catch (RemoteException e) {
			
			throw e;
		} finally {
			
			try {
				output.close();
			} catch (IOException e) {
				throw new RemoteException("Fail to close client output stream", e);
			}
		}
	}
	
	/**
	 * @return the request code
	 */
	abstract byte getCode();

	/**
	 * @param output the output stream
	 * @throws RemoteException if fail to write request to server stream
	 */
	abstract void write(final OutputStream output) throws RemoteException;
}
