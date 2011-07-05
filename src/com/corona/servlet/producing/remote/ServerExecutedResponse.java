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
	 * @param token the new token
	 * @param outcome the outcome
	 * @param marshaller the marshaller
	 */
	ServerExecutedResponse(final Server server, final String token, final Marshaller<T> marshaller, final T outcome) {
		super(server);
		
		this.token = token;
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
			byte[] encryptedToken = this.encryptWithServerKey(this.token.getBytes());
			try {
				output.write((byte) (encryptedToken.length / 256));
				output.write((byte) (encryptedToken.length % 256));
				output.write(encryptedToken);
			} catch (Exception e) {
				throw new RemoteException("Fail to write token length and token to remote client stream", e);
			}
		} else {
			try {
				output.write(new byte[] {0, 0});
			} catch (Exception e) {
				throw new RemoteException("Fail to write empty token length to remote client stream", e);
			}
		}
		
		// write outcome to client output stream if it has
		if (this.outcome != null) {
			try {
				ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
				this.marshaller.marshal(dataStream, this.outcome);
				
				byte[] encryptedData = this.encryptWithClientKey(this.token, dataStream.toByteArray());
				output.write(encryptedData);
			} catch (Exception e) {
				throw new RemoteException("Fail to marshal and encrypt server response to client output stream", e);
			}
		}
	}
}
