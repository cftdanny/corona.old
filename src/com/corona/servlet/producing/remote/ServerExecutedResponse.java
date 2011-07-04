/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.corona.io.Marshaller;
import com.corona.remote.Constants;
import com.corona.remote.RemoteException;
import com.corona.remote.Server;

/**
 * <p>Client request has been executed and send result from server to client </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the type of producing outcome
 */
class ServerExecutedResponse<T> extends AbstractServerResponse {

	/**
	 * the outcome
	 */
	private T outcome;
	
	/**
	 * the token
	 */
	private String token = null;
	
	/**
	 * the marshaller
	 */
	private Marshaller<T> marshaller;
	
	/**
	 * @param server the server
	 * @param outcome the outcome
	 * @param marshaller the marshaller
	 */
	ServerExecutedResponse(final Server server, final Marshaller<T> marshaller, final T outcome) {
		super(server);
		this.marshaller = marshaller;
		this.outcome = outcome;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.ServerResponse#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.RESPONSE.SUCCESS_EXECUTED;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.ServerResponse#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {
		
		// write token if there is a new token is created
		super.write(output);
		if (this.token != null) {
			byte[] data = this.encryptWithServerKey(this.token.getBytes());
			try {
				output.write((byte) (data.length / 256));
				output.write((byte) (data.length % 256));
				output.write(data);
			} catch (Exception e) {
				throw new RemoteException("Fail to write token to remote client stream", e);
			}
		}
		
		// write outcome to client output stream if it has
		if (this.outcome != null) {
			try {
				ByteArrayOutputStream array = new ByteArrayOutputStream();
				this.marshaller.marshal(array, this.outcome);
				output.write(this.encryptWithClientKey(this.token, array.toByteArray()));
			} catch (Exception e) {
				throw new RemoteException("Fail to marshal and encrypt server response to client output stream", e);
			}
		}
	}
}
