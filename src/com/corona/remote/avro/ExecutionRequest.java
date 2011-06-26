/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import java.io.OutputStream;

import com.corona.remote.Constants;
import com.corona.remote.RemoteException;

/**
 * <p>This request is used to execute service in remote server </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ExecutionRequest extends AbstractRequest {

	/**
	 * @param client the client
	 */
	ExecutionRequest(final AvroClient client) {
		super(client);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Request#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.REQUEST.EXECUTE;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Request#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {
	}
}
