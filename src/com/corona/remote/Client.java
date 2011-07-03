/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.util.HashMap;
import java.util.Map;

import com.corona.io.Marshaller;
import com.corona.io.MarshallerFactory;

/**
 * <p>The client that is used to exchange information with remote server </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Client {
	
	/**
	 * the configurator
	 */
	private Configuration configuration;
	
	/**
	 * the current token
	 */
	private String token;
	
	/**
	 * all marshallers
	 */
	private Map<Class<?>, Marshaller<?>> marshallers = new HashMap<Class<?>, Marshaller<?>>();
	
	/**
	 * @param configuration the configuration
	 */
	public Client(final Configuration configuration) {
		this.configuration = configuration;
	}
	
	/**
	 * @return the client and server configuration
	 */
	public Configuration getConfiguration() {
		return this.configuration;
	}

	/**
	 * @return the client framework version
	 */
	public byte getClientLibraryVersion() {
		return (byte) 10;
	}
	
	/**
	 * @return the token that is assigned by server
	 */
	String getToken() {
		return token;
	}

	/**
	 * @param type the class type
	 * @return the marshaller about the class type
	 */
	Marshaller<?> getMarshaller(final Class<?> type) {
		
		Marshaller<?> marshaller = this.marshallers.get(type);
		if (marshaller == null) {
			marshaller = MarshallerFactory.get(this.configuration.getProtocol()).create(type);
			this.marshallers.put(type, marshaller);
		}
		return marshaller;
	}
	
	/**
	 * @param uri the service URI
	 * @return the connection
	 * @throws RemoteException if fail to create connection
	 */
	private Connection getConnection(final String uri) throws RemoteException {
		return new Connection(this.configuration, this.configuration.getLoginURL());
	}
	
	/**
	 * @param username the user name
	 * @param password the password
	 * @throws RemoteException if fail to log in to remote server
	 */
	public void login(final String username, final String password) throws RemoteException {
		
		// create URL connection to connect remote server
		Connection connection = this.getConnection(this.configuration.getLoginURL());
		
		// send request to server in order to execute it
		ClientRequest request = new ClientLoginRequest(this, username, password);
		request.write(connection);
		
		// receive response from server
		Response response = ClientResponseFactory.getResponse(this, connection);
	}
	
	/**
	 * @throws RemoteException if fail to log out from remote server
	 */
	public void logout() throws RemoteException {
		
		// create URL connection to connect remote server
		Connection connection = this.getConnection(this.configuration.getLoginURL());
		
		// send request to server in order to execute it
		ClientRequest request = new ClientLogoutRequest(this);
		request.write(connection);
		
		// receive response from server
		Response response = ClientResponseFactory.getResponse(this, connection);
	}
	
	/**
	 * @param <S> the source type
	 * @param <T> the target type
	 * @param name the function to be called
	 * @param argument the argument for function 
	 * @return the result
	 * @exception RemoteException if fail to execute command in remote server
	 */
	<S, T> T execute(final String name, final S argument) throws RemoteException {
		return null;
	}
}
