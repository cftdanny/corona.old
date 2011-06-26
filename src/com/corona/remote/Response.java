/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.InputStream;

/**
 * <p>The response from server </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Response {

	/**
	 * @return the response code
	 */
	int getCode();
	
	/**
	 * @param input the input from remote server 
	 * @throws RemoteException if fail to read data from server
	 */
	void read(InputStream input) throws RemoteException;
}
