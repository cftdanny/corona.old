/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.corona.io.Marshaller;

/**
 * <p>This request is used to execute service in remote server </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the source type
 */
class ClientExecuteRequest<T> extends AbstractRequest {

	/**
	 * the data to be sent to server
	 */
	private T data;
	
	/**
	 * @param client the client
	 * @param data the data
	 */
	ClientExecuteRequest(final Client client, final T data) {
		super(client);
		this.data = data;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.AbstractRequest#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.REQUEST.EXECUTE;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.AbstractRequest#write(java.io.OutputStream)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void write(final OutputStream output) throws RemoteException {

		// send token to remote server
		try {
			byte[] bytes = this.encryptWithServerKey(this.getClient().getToken().getBytes());
			output.write((byte) bytes.length);
			output.write(bytes);
		} catch (IOException e) {
			throw new RemoteException("Fail to write token to client output stream", e);
		}

		// if data is not null, will marshal data and send to server
		if (this.data != null) {
			
			Marshaller<T> marshaller = (Marshaller<T>) this.getClient().getMarshaller(data.getClass());
			try {
				ByteArrayOutputStream array = new ByteArrayOutputStream();
				marshaller.marshal(array, this.data);
				
				byte[] bytes = this.encryptWithClientKey(array.toByteArray());
				output.write(bytes);
			} catch (Exception e) {
				throw new RemoteException("Fail to write request data to client output stream", e);
			}
		}
	}
}
