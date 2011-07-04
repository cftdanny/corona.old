/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import java.io.InputStream;

import com.corona.remote.Constants;
import com.corona.remote.RemoteException;
import com.corona.remote.ClientResponse;

/**
 * <p>User has logged in into server </p>
 *
 * @author $Author$
 * @version $Id$
 */
class LogoutResponse implements ClientResponse {

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientResponse#getCode()
	 */
	@Override
	public int getCode() {
		return Constants.RESPONSE.LOGGED_OUT;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientResponse#read(java.io.InputStream)
	 */
	@Override
	public void read(final InputStream input) throws RemoteException {
	}
}
