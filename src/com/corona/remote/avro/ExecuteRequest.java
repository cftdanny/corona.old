/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.corona.remote.AbstractRequest;
import com.corona.remote.Constants;
import com.corona.remote.Context;
import com.corona.remote.RemoteException;

/**
 * <p>This request is used to execute service in remote server </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <S> the source type
 */
class ExecuteRequest<S> extends AbstractServerRequest {

	/**
	 * the context
	 */
	private Context<S, ?> context;
	
	/**
	 * the data to be marshalled and send to server
	 */
	private S data;
	
	/**
	 * @param client the client
	 * @param context the context
	 * @param data the data
	 */
	ExecuteRequest(final AvroClient client, final Context<S, ?> context, final S data) {
		super(client);
		
		this.context = context;
		this.data = data;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientRequest#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.REQUEST.EXECUTE;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.AbstractRequest#getClient()
	 */
	@Override
	protected AvroClient getClient() {
		return (AvroClient) super.getClient();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.ClientRequest#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {

		// send production or development mode or action to server
		this.sendModeAndAction(output);

		// send token to remote server
		try {
			byte[] bytes = this.encryptWithServerKey(this.getClient().getToken().getBytes());
			output.write((byte) bytes.length);
			output.write(bytes);
		} catch (IOException e) {
			throw new RemoteException("Fail to send client token to remote server", e);
		}

		// if data is null, don't need send data to remote server
		if (this.data != null) {
			
			// send data to remote server
			if (this.getClient().hasClientCypher()) {
				// if production mode, need ecrypt data before send
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					this.context.getMarshaller().marshal(baos, this.data);
					
					byte[] bytes = this.encryptWithClientKey(baos.toByteArray());
					output.write(bytes);
				} catch (Exception e) {
					throw new RemoteException("Fail to marshal and ecrypt data object to remote server", e);
				}
			} else {
				// if development mode, encryption cipher is null, don't need encrypt
				try {
					this.context.getMarshaller().marshal(output, this.data);
				} catch (Exception e) {
					throw new RemoteException("Fail to marshal data object to remote server", e);
				}
			}
		}
	}
}
