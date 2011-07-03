/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

/**
 * <p>Read output from remote server and convert it to response </p>
 *
 * @author $Author$
 * @version $Id$
 */
final class ClientResponseFactory {

	/**
	 * utility class
	 */
	private ClientResponseFactory() {
		// do nothing
	}
	
	/**
	 * @param client the client
	 * @param connection the connection
	 * @return the response from server
	 * @exception RemoteException if fail to read server data to response
	 */
	static Response getResponse(final Client client, final Connection connection) throws RemoteException {
		return null;
	}
}
