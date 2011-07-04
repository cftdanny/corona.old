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
class ClientInvalidActionResponse extends AbstractResponse {

	/**
	 * the invalid request action code
	 */
	private byte invalidCode;
	
	/**
	 * @param client the client
	 */
	ClientInvalidActionResponse(final Client client) {
		super(client);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientResponse#getCode()
	 */
	@Override
	public int getCode() {
		return Constants.RESPONSE.INVALID_REQUEST;
	}
	
	/**
	 * @return the invalid request action code
	 */
	public byte getInvalidCode() {
		return invalidCode;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientResponse#read(java.io.InputStream)
	 */
	@Override
	public void read(final InputStream input) throws RemoteException {
		
		try {
			int code = input.read();
			if (code == -1) {
				throw new RemoteException("Should can read invalid request code but client input stream is empty");
			}
			this.invalidCode = (byte) code;
		} catch (IOException e) {
			throw new RemoteException("Fail to read invalid request code from client input stream", e);
		}
	}
}
