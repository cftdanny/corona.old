/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import java.io.IOException;
import java.io.OutputStream;

import com.corona.remote.AbstractRequest;
import com.corona.remote.Constants;
import com.corona.remote.RemoteException;

/**
 * <p>this request is used to send a log out request to server </p>
 *
 * @author $Author$
 * @version $Id$
 */
class LogoutRequest extends AbstractRequest {

	/**
	 * @param client the client
	 */
	LogoutRequest(final AvroClient client) {
		super(client);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Request#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.REQUEST.LOGOUT;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.AbstractRequest#getClient()
	 */
	@Override
	protected AvroClient getClient() {
		return (AvroClient) super.getClient();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Request#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {
		
		// send production or development mode or action to server
		this.sendModeAndAction(output);
		
		// send for logged out token to remote server
		try {
			output.write(this.encryptWithServerKey(this.getClient().getToken().getBytes()));
		} catch (IOException e) {
			throw new RemoteException("Fail to send stream data to remote server", e);
		}
	}
}
