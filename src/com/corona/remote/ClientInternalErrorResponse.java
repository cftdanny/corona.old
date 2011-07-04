/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.InputStream;

/**
 * <p>The response from server that user can't logged in server </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ClientInternalErrorResponse extends AbstractResponse {

	/**
	 * the reason why can't logged in that sends from server
	 */
	private String message;
	
	/**
	 * @param client the client
	 */
	ClientInternalErrorResponse(final Client client) {
		super(client);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientResponse#getCode()
	 */
	@Override
	public int getCode() {
		return Constants.RESPONSE.INTERNAL_ERROR;
	}
	
	/**
	 * @return the reason why can't logged in that sends from server
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientResponse#read(java.io.InputStream)
	 */
	@Override
	public void read(final InputStream input) throws RemoteException {
		
		byte[] data = this.getBytes(input);
		this.message = new String(this.decryptWithServerKey(data));
	}
}
