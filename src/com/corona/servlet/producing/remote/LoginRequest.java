/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.IOException;
import java.io.InputStream;

import com.corona.remote.RemoteException;
import com.corona.remote.Server;

/**
 * <p>Log in request from client </p>
 *
 * @author $Author$
 * @version $Id$
 */
class LoginRequest extends AbstractRequest {

	/**
	 * the client version
	 */
	private byte version = 10;
	
	/**
	 * the user name
	 */
	private String username;
	
	/**
	 * the password
	 */
	private String password;
	
	/**
	 * @param server the server
	 */
	LoginRequest(final Server server) {
		super(server);
	}
	
	/**
	 * @return the client version
	 */
	public byte getVersion() {
		return version;
	}
	
	/**
	 * @return the user name
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.Request#getCode()
	 */
	@Override
	public byte getCode() {
		return Request.LOGIN;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.Request#read(java.io.InputStream)
	 */
	@Override
	public void read(final InputStream input) throws RemoteException {
		
		// read version from remote client
		int i = -1;
		try {
			i = input.read();
		} catch (IOException e) {
			throw new RemoteException("Fail to read data from client stream", e);
		}
		if (i == -1) {
			throw new RemoteException("Should read version from client stream but can't");
		}
		this.version = (byte) i;
		
		// read user name and password from remote client
		String[] values = new String(this.decryptWithServerKey(this.getBytes(input))).split("/");
		if (values.length == 1) {
			this.username = values[0];
			this.password = null;
		} else {
			this.username = values[0];
			this.password = values[1];
		}
	}
}
