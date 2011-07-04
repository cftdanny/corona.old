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
 * <p>This response will send message to client that there is an internal error in server side </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ServerInternalErrorResponse extends AbstractServerResponse {

	/**
	 * the error message
	 */
	private String error;
	
	/**
	 * @param server the server
	 * @param error error message
	 */
	ServerInternalErrorResponse(final Server server, final String error) {
		super(server);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.AbstractServerResponse#getCode()
	 */
	@Override
	protected byte getCode() {
		return Constants.RESPONSE.INTERNAL_ERROR;
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
