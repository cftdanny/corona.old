/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.OutputStream;

/**
 * <p>The log in request </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ClientLoginRequest extends AbstractRequest {

	/**
	 * the user identity
	 */
	private String identity;

	/**
	 * @param client the client
	 * @param username the user name
	 * @param password the password
	 */
	ClientLoginRequest(final Client client, final String username, final String password) {
		super(client);
		this.identity = username + "/" + password;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.AbstractRequest#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.REQUEST.LOGIN;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.AbstractRequest#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {
		
		// send client library version, user name and password to remote server
		try {
			output.write(this.getClient().getClientLibraryVersion());
			output.write(this.encryptWithServerKey(this.identity.getBytes()));
		} catch (Exception e) {
			throw new RemoteException("Fail to send log in request data to server", e);
		}
	}
}
