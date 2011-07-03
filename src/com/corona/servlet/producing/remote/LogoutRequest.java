/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.InputStream;

import com.corona.remote.RemoteException;
import com.corona.remote.Server;

/**
 * <p>Log out request sent from client </p>
 *
 * @author $Author$
 * @version $Id$
 */
class LogoutRequest extends AbstractRequest {

	/**
	 * the token
	 */
	private String token;
	
	/**
	 * @param server the server
	 */
	LogoutRequest(final Server server) {
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
	 * @see com.corona.servlet.producing.remote.Request#getCode()
	 */
	@Override
	public byte getCode() {
		return Request.LOGOUT;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.Request#read(java.io.InputStream)
	 */
	@Override
	public void read(final InputStream input) throws RemoteException {
		this.token = new String(this.decryptWithServerKey(this.getBytes(input)));
	}
}
