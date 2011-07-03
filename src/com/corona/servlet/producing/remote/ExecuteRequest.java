/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.corona.crypto.CypherException;
import com.corona.io.UnmarshalException;
import com.corona.io.Unmarshaller;
import com.corona.remote.RemoteException;
import com.corona.remote.Server;

/**
 * <p>Log in request from client </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ExecuteRequest extends AbstractRequest {

	/**
	 * the unmarshaller to unmarshal request into data
	 */
	@SuppressWarnings("rawtypes")
	private Unmarshaller unmarshaller;
	
	/**
	 * the client token
	 */
	private String token;
	
	/**
	 * the data
	 */
	private Object data = null;
	
	/**
	 * @param server the server
	 * @param unmarshaller the unmarshaller to unmarshal request data
	 */
	@SuppressWarnings("rawtypes")
	ExecuteRequest(final Server server, final Unmarshaller unmarshaller) {
		super(server);
	}
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
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
		
		// read token length (2 bytes) from client stream
		int length = 0;
		try {
			int l1 = input.read();
			int l2 = input.read();
			if ((l1 == -1) || (l2 == -1)) {
				throw new RemoteException("Should read token length from client stream but can't");
			}
			length = l1 * 256 + l2;
		} catch (IOException e) {
			throw new RemoteException("Fail to read data from client stream", e);
		}
		
		// read token from client stream and then decrypt it by server key
		this.token = new String(this.decryptWithServerKey(this.getBytes(input, length)));
		
		// read request stream of data into bytes value
		byte[] values = null;
		try {
			values = this.getServer().getClientCypher(this.token).decrypt(this.getBytes(input));
		} catch (CypherException e) {
			throw new RemoteException("Fail to decrypt data from remote client stream", e);
		}
		
		if ((values != null) && (values.length > 0)) {
			try {
				this.data = this.unmarshaller.unmarshal(new ByteArrayInputStream(values));
			} catch (UnmarshalException e) {
				throw new RemoteException("Fail to unmarshal client stream to request object data", e);
			}
		} else {
			this.data = null;
		}
	}
}
