/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

/**
 * <p>The request sents to server </p>
 *
 * @author $Author$
 * @version $Id$
 */
interface ClientRequest {
	
	/**
	 * @param connection the connection
	 * @throws RemoteException if fail to send data to server
	 */
	void write(Connection connection) throws RemoteException;
}
