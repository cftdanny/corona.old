/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>this request is used to send a log out request to server </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ClientLogoutRequest extends AbstractRequest {

	/**
	 * @param client the client
	 */
	ClientLogoutRequest(final Client client) {
		super(client);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientRequest#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.REQUEST.LOGOUT;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientRequest#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {
		
		// send for logged out token to remote server
		try {
			byte[] data = this.encryptWithServerKey(this.getClient().getToken().getBytes());
			output.write(data);
		} catch (IOException e) {
			throw new RemoteException("Fail to send log out request data to client output stream", e);
		}
	}
}
