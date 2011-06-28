/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import java.io.OutputStream;

import com.corona.remote.AbstractRequest;
import com.corona.remote.Constants;
import com.corona.remote.RemoteException;

/**
 * <p>The log in request </p>
 *
 * @author $Author$
 * @version $Id$
 */
class LoginRequest extends AbstractRequest {

	/**
	 * the user identity
	 */
	private String identity;

	/**
	 * @param client the client
	 * @param username the user name
	 * @param password the password
	 */
	LoginRequest(final AvroClient client, final String username, final String password) {
		super(client);
		this.identity = username + "/" + password;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Request#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.REQUEST.LOGIN;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Request#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {
		
		// send production or development mode or action to server
		this.sendModeAndAction(output);
		
		// send client framework version, user name and password to remote server
		try {
			output.write(this.getClient().getClientFrameworkVersion());
			output.write(this.encryptWithServerKey(this.identity.getBytes()));
		} catch (Exception e) {
			throw new RemoteException("Fail to write stream data to remote server", e);
		}
	}
}
