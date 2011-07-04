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
class ServerInvalidActionResponse extends AbstractServerResponse {

	/**
	 * the invalid request code
	 */
	private byte invalidCode;
	
	/**
	 * @param server the server
	 * @param invalidCode the invalid request code
	 */
	ServerInvalidActionResponse(final Server server, final byte invalidCode) {
		super(server);
		this.invalidCode = invalidCode;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.AbstractServerResponse#getCode()
	 */
	@Override
	protected byte getCode() {
		return Constants.RESPONSE.INVALID_REQUEST;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.AbstractServerResponse#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {
		
		super.write(output);
		try {
			output.write(this.invalidCode);
		} catch (IOException e) {
			throw new RemoteException("Fail to write invalid code to client output stream", e);
		}
	}
}
