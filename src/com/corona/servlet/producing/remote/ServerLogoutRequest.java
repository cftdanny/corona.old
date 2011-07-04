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
class ServerLogoutRequest extends AbstractServerRequest {

	/**
	 * the token
	 */
	private String token;
	
	/**
	 * @param server the server
	 */
	ServerLogoutRequest(final Server server) {
		super(server);
	}
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.ServerRequest#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.REQUEST.LOGOUT;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.ServerRequest#read(java.io.InputStream)
	 */
	@Override
	public void read(final InputStream input) throws RemoteException {
		
		byte[] bytes = this.getBytes(input);
		this.token = new String(this.decryptWithServerKey(bytes));
	}
}
