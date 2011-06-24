/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import java.io.IOException;
import java.io.OutputStream;

import com.corona.remote.Request;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
class AvroLoginRequest implements Request {

	/**
	 * the client
	 */
	private AvroClient client;
	
	private String username;
	
	private String password;
	
	/**
	 * @param client the client
	 */
	AvroLoginRequest(final AvroClient client, final String username, final String password) {
		this.client = client;
		this.username = username;
		this.password = password;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Request#getCode()
	 */
	@Override
	public byte getCode() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Request#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream out) throws IOException {
	}
}
