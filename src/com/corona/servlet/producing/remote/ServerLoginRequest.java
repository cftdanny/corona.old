/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.IOException;
import java.io.InputStream;

import com.corona.remote.Constants;
import com.corona.remote.RemoteException;
import com.corona.remote.Server;

/**
 * <p>Log in request from client </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ServerLoginRequest extends AbstractServerRequest {

	/**
	 * the client library version
	 */
	private byte clientLibraryVersion = 10;
	
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
	ServerLoginRequest(final Server server) {
		super(server);
	}
	
	/**
	 * @return the client library version
	 */
	public byte getClientLibraryVersion() {
		return clientLibraryVersion;
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
	 * @see com.corona.servlet.producing.remote.ServerRequest#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.REQUEST.LOGIN;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.ServerRequest#read(java.io.InputStream)
	 */
	@Override
	public void read(final InputStream input) throws RemoteException {
		
		// read version from client input stream
		int i = -1;
		try {
			i = input.read();
		} catch (IOException e) {
			throw new RemoteException("Fail to read client library version from client input stream", e);
		}
		if (i == -1) {
			throw new RemoteException("Should read client library version from client input stream but can't");
		}
		this.clientLibraryVersion = (byte) i;
		
		// read user name and password from client input stream
		byte[] bytes = this.getBytes(input);
		String[] values = new String(this.decryptWithServerKey(bytes)).split("/");
		if (values.length == 1) {
			this.username = values[0];
			this.password = null;
		} else {
			this.username = values[0];
			this.password = values[1];
		}
	}
}
