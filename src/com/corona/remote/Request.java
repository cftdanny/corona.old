/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.OutputStream;

/**
 * <p>The request will be executed at server </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Request {

	/**
	 * @return the request code
	 */
	byte getCode();
	
	/**
	 * @param output the output stream to send data to server
	 * @throws RemoteException if fail to send data to server
	 */
	void write(OutputStream output) throws RemoteException;
}
