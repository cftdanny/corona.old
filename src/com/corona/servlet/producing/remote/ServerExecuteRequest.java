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
import com.corona.remote.Constants;
import com.corona.remote.RemoteException;
import com.corona.remote.Server;

/**
 * <p>Log in request from client </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ServerExecuteRequest extends AbstractServerRequest {

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
	ServerExecuteRequest(final Server server, final Unmarshaller unmarshaller) {
		super(server);
		this.unmarshaller = unmarshaller;
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
	 * @see com.corona.servlet.producing.remote.ServerRequest#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.REQUEST.EXECUTE;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.ServerRequest#read(java.io.InputStream)
	 */
	@Override
	public void read(final InputStream input) throws RemoteException {
		
		// read token length (2 bytes) from client stream
		int length = 0;
		try {
			int l1 = input.read();
			int l2 = input.read();
			if ((l1 == -1) || (l2 == -1)) {
				throw new RemoteException("Should read token length from client input stream but can't");
			}
			length = l1 * 256 + l2;
		} catch (IOException e) {
			throw new RemoteException("Fail to read decrypted token from client input stream", e);
		}
		
		// read token from client stream and then decrypt it by server key
		this.token = new String(this.decryptWithServerKey(this.getBytes(input, length)));
		
		// read request input stream, if it isn't empty, will decrypt and unmarshal to request data object
		byte[] values = this.getBytes(input);
		if ((values != null) && (values.length > 0)) {
			// decrypt read data by client key
			try {
				values = this.getServer().getClientCypher(this.token).decrypt(values);
			} catch (CypherException e) {
				throw new RemoteException("Fail to decrypt request data read from remote client stream", e);
			}
		
			// unmarshal decrypted into request data object
			try {
				this.data = this.unmarshaller.unmarshal(new ByteArrayInputStream(values));
			} catch (UnmarshalException e) {
				throw new RemoteException("Fail to unmarshal client input stream to request data object", e);
			}
		}
	}
}
