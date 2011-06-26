/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

/**
 * <p>The client that is used to exchange information with remote server </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Client {

	/**
	 * @return the client framework version
	 */
	byte getClientFrameworkVersion();
	
	/**
	 * @return the server framework version
	 */
	byte getServerFrameworkVersion();
	
	/**
	 * @param username the user name
	 * @param password the password
	 * @throws RemoteException if fail to log in to remote server
	 */
	void login(String username, String password) throws RemoteException;
	
	/**
	 * @throws RemoteException if fail to log out from remote server
	 */
	void logout() throws RemoteException;
	
	/**
	 * @param name the function to be called
	 * @param argument the argument for function 
	 * @return the result
	 */
	Object execute(final String name, final Object argument);
}
