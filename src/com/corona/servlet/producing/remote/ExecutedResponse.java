/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.corona.io.Marshaller;
import com.corona.remote.Constants;
import com.corona.remote.RemoteException;

/**
 * <p>Client request has been executed and send result from server to client </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the type of producing outcome
 */
class ExecutedResponse<T> extends AbstractResponse {

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
	 * @param outcome the outcome
	 * @param marshaller the marshaller
	 */
	ExecutedResponse(final Marshaller<T> marshaller, final T outcome) {
		this.marshaller = marshaller;
		this.outcome = outcome;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.Response#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.RESPONSE.SUCCESS_EXECUTED;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.Response#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {
		
		this.writeModeAndAction(output);
		
		// write token if there is a new token is created
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
		
		// write producing outcome to client stream if it has
		if (this.outcome != null) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				this.marshaller.marshal(baos, this.outcome);
				output.write(this.encryptWithClientKey(this.token, baos.toByteArray()));
			} catch (Exception e) {
				throw new RemoteException("Fail to marshal and encrypt response data to client", e);
			}
		}
	}
}
