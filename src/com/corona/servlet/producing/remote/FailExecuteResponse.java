/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.OutputStream;
import java.io.PrintStream;

import com.corona.remote.Constants;
import com.corona.remote.RemoteException;

/**
 * <p>This response is used to tell client an error occurs when execute request. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class FailExecuteResponse extends AbstractResponse {

	/**
	 * the exception
	 */
	private Throwable e;
	
	/**
	 * @param e the exception
	 */
	FailExecuteResponse(final Throwable e) {
		this.e = e;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.Response#getCode()
	 */
	@Override
	public byte getCode() {
		return Constants.RESPONSE.FAIL_EXECUTED;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.Response#write(java.io.OutputStream)
	 */
	@Override
	public void write(final OutputStream output) throws RemoteException {
		
		this.writeModeAndAction(output);
		try {
			e.printStackTrace(new PrintStream(output));
		} catch (Exception ex) {
			throw new RemoteException("Fail to write error stack trace to response stream", ex);
		}
	}
}
