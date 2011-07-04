/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.corona.remote.Constants;
import com.corona.remote.RemoteException;
import com.corona.remote.Server;

/**
 * <p>This response is used to tell client an error occurs when execute request. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ServerFailExecuteResponse extends AbstractServerResponse {

	/**
	 * the exception
	 */
	private Throwable e;
	
	/**
	 * @param server the server
	 * @param e the exception
	 */
	ServerFailExecuteResponse(final Server server, final Throwable e) {
		super(server);
		this.e = e;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.AbstractServerResponse#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.RESPONSE.FAIL_EXECUTED;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.ServerResponse#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {
		
		super.write(output);
		try {
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			
			byte[] data = this.encryptWithServerKey(writer.toString().getBytes());
			output.write(data);
		} catch (Exception ex) {
			throw new RemoteException("Fail to write error stack trace to client output stream", ex);
		}
	}
}
