/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>The connection for input and output between server and client </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Connection {

	/**
	 * @return the input stream from server
	 */
	InputStream getInputStream();
	
	/**
	 * @return the output stream to server
	 */
	OutputStream getOutputStream();
	
	/**
	 * @throws RemoteException if fail to close input or output stream
	 */
	void close() throws RemoteException;
}
