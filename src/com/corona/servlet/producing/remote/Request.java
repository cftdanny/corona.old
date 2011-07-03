/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.InputStream;

import com.corona.remote.RemoteException;

/**
 * <p>The request from remote client </p>
 *
 * @author $Author$
 * @version $Id$
 */
interface Request {
	
	/**
	 * the production mode
	 */
	byte PRODUCTION = 80;

	/**
	 * the development mode
	 */
	byte DEVELOPMENT = 90;
	
	/**
	 * the log in request
	 */
	byte LOGIN = 1;
	
	/**
	 * the log out request
	 */
	byte LOGOUT = 2;
	
	/**
	 * execute command request
	 */
	byte EXECUTE = 3;
	
	/**
	 * @return the code
	 */
	byte getCode();
	
	/**
	 * @param input the input stream from remote client
	 * @throws RemoteException if fail to read data from client stream to this request
	 */
	void read(InputStream input) throws RemoteException;
}
