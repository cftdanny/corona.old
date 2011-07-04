/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.InputStream;

import com.corona.remote.Constants;
import com.corona.remote.RemoteException;
import com.corona.remote.Server;

/**
 * <p>Log out request sent from client </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ServerInvalidActionRequest extends AbstractServerRequest {

	/**
	 * the invalid request code
	 */
	private byte invalidCode;
	
	/**
	 * @param server the server
	 * @param invalidCode the invalid request code
	 */
	ServerInvalidActionRequest(final Server server, final byte invalidCode) {
		super(server);
		this.invalidCode = invalidCode;
	}
	
	/**
	 * @return the invalid request code
	 */
	public byte getInvalidCode() {
		return invalidCode;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.ServerRequest#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.REQUEST.INVALID;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.ServerRequest#read(java.io.InputStream)
	 */
	@Override
	public void read(final InputStream input) throws RemoteException {
	}
}
