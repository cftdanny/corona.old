/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.OutputStream;

import com.corona.remote.RemoteException;

/**
 * <p>Send executed response from server to client </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ServerResponse {
	
	/**
	 * @param output the output
	 * @throws RemoteException if fail write data to response
	 */
	void write(OutputStream output) throws RemoteException;
}
