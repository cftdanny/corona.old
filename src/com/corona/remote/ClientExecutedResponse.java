/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.corona.io.UnmarshalException;
import com.corona.io.Unmarshaller;

/**
 * <p>Process response from server that return with data and maybe, new token </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the target type
 */
class ClientExecutedResponse<T> extends AbstractResponse {

	/**
	 * the token
	 */
	private String token = null;
	
	/**
	 * the unmarshaller
	 */
	private Unmarshaller<T> unmarshaller;
	
	/**
	 * the value
	 */
	private T value = null;
	
	/**
	 * @param client the client
	 * @param unmarshaller the unmarshaller to unmarshal return data  
	 */
	ClientExecutedResponse(final Client client, final Unmarshaller<T> unmarshaller) {
		super(client);
		this.unmarshaller = unmarshaller;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientResponse#getCode()
	 */
	@Override
	public int getCode() {
		return Constants.RESPONSE.SUCCESS_EXECUTED;
	}
	
	/**
	 * @return the new token or null
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientResponse#read(java.io.InputStream)
	 */
	@Override
	public void read(final InputStream input) throws RemoteException {
		
		// read token length byte, should read high length and low length
		int length;
		try {
			int hilength = input.read();
			int lolength = input.read();
			if ((hilength == -1) || (lolength == -1)) {
				throw new RemoteException("Should can read token lenght but client input stream is empty");
			}
			length = hilength * 256 + lolength;
		} catch (IOException e) {
			throw new RemoteException("Fail to read token length from client input stream", e);
		}
			
		// read token from client by token length read before
		byte[] data = this.getBytes(input, length);
		this.token = new String(this.decryptWithServerKey(data));
		
		// read response data from client input stream
		data = this.getBytes(input);
		if (data.length > 0) {
			
			// decrypt response data with client key and unmarshal to response object 
			data = this.decryptWithClientKey(data);
			try {
				this.value = this.unmarshaller.unmarshal(new ByteArrayInputStream(data));
			} catch (UnmarshalException e) {
				throw new RemoteException("Fail to decrypt or unmarshal client input stream to response object", e);
			}
		}
	}
}
