/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import java.io.InputStream;

import com.corona.remote.AbstractResponse;
import com.corona.remote.Constants;
import com.corona.remote.RemoteException;

/**
 * <p>Fail to execute command and return exception message </p>
 *
 * @author $Author$
 * @version $Id$
 */
class FailExecutedResponse extends AbstractResponse {

	/**
	 * the reason why can't logged in that sends from server
	 */
	private String message;
	
	/**
	 * @param client the client
	 */
	FailExecutedResponse(final AvroClient client) {
		super(client);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientResponse#getCode()
	 */
	@Override
	public int getCode() {
		return Constants.RESPONSE.FAIL_EXECUTED;
	}
	
	/**
	 * @return the reason why can't logged in that sends from server
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientResponse#read(java.io.InputStream)
	 */
	@Override
	public void read(final InputStream input) throws RemoteException {
		this.message = new String(this.decryptWithClientKey(this.getBytes(input)));
	}
}
