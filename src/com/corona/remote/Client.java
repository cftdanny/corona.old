/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.util.HashMap;
import java.util.Map;

import com.corona.io.Marshaller;
import com.corona.io.MarshallerFactory;
import com.corona.io.Unmarshaller;
import com.corona.io.UnmarshallerFactory;

/**
 * <p>The client that is used to exchange information with remote server </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Client {
	
	/**
	 * the configuration
	 */
	private Configuration configuration;
	
	/**
	 * the current token
	 */
	private String token;
	
	/**
	 * the server library version
	 */
	private byte serverLibraryVersion = -1;
	
	/**
	 * all marshallers
	 */
	private Map<Class<?>, Marshaller<?>> marshallers = new HashMap<Class<?>, Marshaller<?>>();
	
	/**
	 * all unmarshallers
	 */
	private Map<Class<?>, Unmarshaller<?>> unmarshallers = new HashMap<Class<?>, Unmarshaller<?>>();
	
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
		return Constants.LIBRARY_VESION;
	}
	
	/**
	 * @return the server library version
	 */
	public byte getServerLibraryVersion() {
		return serverLibraryVersion;
	}
	
	/**
	 * @param serverLibraryVersion the server library version to set
	 */
	void setServerLibraryVersion(final byte serverLibraryVersion) {
		this.serverLibraryVersion = serverLibraryVersion;
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
		ClientResponse response = ClientResponseFactory.getResponse(this, connection, null);
		switch (response.getCode()) {
			
			case Constants.RESPONSE.LOGGED_IN:
				this.token = ((ClientLoggedInResponse) response).getToken();
				this.serverLibraryVersion = ((ClientLoggedInResponse) response).getServerLibraryVersion();
				break;
				
			case Constants.RESPONSE.CANT_LOGGED_IN:
				throw new RemoteException(((ClientCantLoggedInResponse) response).getMessage());
				
			default:
				throw new RemoteException(
						"Unexpect response [{0}:{1}] received from server", response.getCode(), response.getClass() 
				);
		}
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
		
		// receive response from server and should be logged out
		ClientResponse response = ClientResponseFactory.getResponse(this, connection, null);
		if (response.getCode() != Constants.RESPONSE.LOGGED_OUT) {
			throw new RemoteException(
					"Unexpect response [{0}:{1}] received from server", response.getCode(), response.getClass() 
			);
		}
	}

	/**
	 * @param <T> the class type
	 * @param type the class type
	 * @return the marshaller about the class type
	 */
	@SuppressWarnings("unchecked")
	private <T> Unmarshaller<T> getUnmarshaller(final Class<T> type) {
		
		Unmarshaller<T> unmarshaller = (Unmarshaller<T>) this.unmarshallers.get(type);
		if (unmarshaller == null) {
			unmarshaller = UnmarshallerFactory.get(this.configuration.getProtocol()).create(type);
			this.unmarshallers.put(type, unmarshaller);
		}
		return unmarshaller;
	}

	/**
	 * @param <S> the source type
	 * @param <T> the target type
	 * @param name the function to be called
	 * @param argument the argument for function 
	 * @param returnType the class type of return value
	 * @return the result
	 * @exception RemoteException if fail to execute command in remote server
	 */
	@SuppressWarnings("unchecked")
	<S, T> T execute(final String name, final S argument, final Class<T> returnType) throws RemoteException {

		// create URL connection to connect remote server
		Connection connection = this.getConnection(this.configuration.getLoginURL());
		
		// send request to server in order to execute it
		ClientRequest request = new ClientExecuteRequest<S>(this, argument);
		request.write(connection);

		// if there is return type, get or create unmarshaller for it
		Unmarshaller<T> unmarshaller = null;
		if (returnType != null) {
			unmarshaller = this.getUnmarshaller(returnType);
		}
		
		// receive response from server and should be logged out
		ClientResponse response = ClientResponseFactory.getResponse(this, connection, unmarshaller);
		if (response.getCode() != Constants.RESPONSE.SUCCESS_EXECUTED) {
			throw new RemoteException(
					"Unexpect response [{0}:{1}] received from server", response.getCode(), response.getClass() 
			);
		}

		// cast response to successfully executed response
		ClientExecutedResponse<T> executed = (ClientExecutedResponse<T>) response;
		if (executed.getToken() != null) {
			this.token = executed.getToken();
		}
		
		return executed.getValue();
	}
}
