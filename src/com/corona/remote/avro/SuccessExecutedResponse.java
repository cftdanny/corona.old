/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.corona.io.UnmarshalException;
import com.corona.io.Unmarshaller;
import com.corona.remote.AbstractResponse;
import com.corona.remote.Constants;
import com.corona.remote.RemoteException;

/**
 * <p>Process response from server that return with data and maybe, new token </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the target type
 */
class SuccessExecutedResponse<T> extends AbstractResponse {

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
	SuccessExecutedResponse(final AvroClient client, final Unmarshaller<T> unmarshaller) {
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
		
		// read token length byte
		int length;
		try {
			length = input.read();
		} catch (IOException e) {
			throw new RemoteException("Fail to read data from input stream return by server", e);
		}
			
		// should not -1, if > 0, will read new token
		if (length == -1) {
			throw new RemoteException(
					"Should can read data from input stream return by server, but read nothing"
			);
		} else if (length > 0) {
			
			byte[] bytes = this.getBytes(input, length);
			this.token = new String(this.decryptWithServerKey(bytes));
		}
		
		// marshal returned data from server to object
		byte[] data = this.getBytes(input);
		if (data.length > 0) {
			
			ByteArrayInputStream bais;
			if (this.getClient().hasClientCypher()) {
				bais = new ByteArrayInputStream(this.decryptWithClientKey(data));
			} else {
				bais = new ByteArrayInputStream(data);
			}
			try {
				this.value = this.unmarshaller.unmarshal(bais);
			} catch (UnmarshalException e) {
				throw new RemoteException("Fail to unmarshal data return by server to object", e);
			}
		}
	}
}
