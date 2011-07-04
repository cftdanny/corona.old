/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.IOException;
import java.io.OutputStream;

import com.corona.remote.Constants;
import com.corona.remote.RemoteException;
import com.corona.remote.Server;

/**
 * <p>This response is used to send message to client that fail to log in server </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ServerCantLoggedInResponse extends AbstractServerResponse {

	/**
	 * the error message why can't log in
	 */
	private String error;
	
	/**
	 * @param server the server
	 * @param error the error message
	 */
	ServerCantLoggedInResponse(final Server server, final String error) {
		super(server);
		this.error = error;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.AbstractServerResponse#getCode()
	 */
	@Override
	protected byte getCode() {
		return Constants.RESPONSE.CANT_LOGGED_IN;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.AbstractServerResponse#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {

		super.write(output);
		try {
			output.write(this.encryptWithServerKey(this.error.getBytes()));
		} catch (IOException e) {
			throw new RemoteException("Fail to write error message to client output stream", e);
		}
	}
}
