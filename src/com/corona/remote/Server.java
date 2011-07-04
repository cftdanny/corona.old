/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import com.corona.crypto.Cypher;

/**
 * <p>The server in order to exchange data with remote client </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Server {

	/**
	 * @return the server cypher
	 * @throws RemoteException if fail to create server cypher
	 */
	Cypher getServerCypher() throws RemoteException;
	
	/**
	 * @param token the token assigned for client
	 * @return the client cypher
	 * @throws RemoteException if fail to create client cypher
	 */
	Cypher getClientCypher(String token) throws RemoteException;
	
	/**
	 * @param token the current token
	 * @return the new token
	 */
	String getToken(String token);
	
	/**
	 * @param version the client version
	 * @return whether server supports the version of client
	 */
	boolean isSupportedVersion(final byte version);
}
