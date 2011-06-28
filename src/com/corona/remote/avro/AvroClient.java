/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.corona.io.Marshaller;
import com.corona.io.Unmarshaller;
import com.corona.io.avro.AvroMarshallerFactory;
import com.corona.io.avro.AvroUnmarshallerFactory;
import com.corona.remote.AbstractClient;
import com.corona.remote.Configurator;
import com.corona.remote.Connection;
import com.corona.remote.Constants;
import com.corona.remote.Context;
import com.corona.remote.RemoteException;
import com.corona.remote.Request;
import com.corona.remote.Response;

/**
 * <p>The implementation of client by Apache Avro </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AvroClient extends AbstractClient {

	/**
	 * the server framework version
	 */
	private byte serverFrameworkVersion;

	/**
	 * all marshallers about source type
	 */
	private Map<Class<?>, Marshaller<?>> marshallers = new HashMap<Class<?>, Marshaller<?>>();
	
	/**
	 * all unmarshaller about target type
	 */
	private Map<Class<?>, Unmarshaller<?>> unmarshallers = new HashMap<Class<?>, Unmarshaller<?>>();
	
	/**
	 * the token that is assigned by server
	 */
	private String token;
	
	/**
	 * @param configurator the configurator
	 * @exception RemoteException if fail to create client
	 */
	AvroClient(final Configurator configurator) throws RemoteException {
		super(configurator);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Client#getServerFrameworkVersion()
	 */
	@Override
	public byte getServerFrameworkVersion() {
		return this.serverFrameworkVersion;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Client#getClientFrameworkVersion()
	 */
	public byte getClientFrameworkVersion() {
		return 10;
	}

	/**
	 * @return whether it is production mode
	 */
	boolean isProductionMode() {
		return this.getClientCipher() != null;
	}
	
	/**
	 * @return the token that feedback from server
	 */
	public String getToken() {
		return token;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.AbstractClient#getConnection(java.lang.String)
	 */
	@Override
	protected Connection getConnection(final String serviceName) throws RemoteException {
		return new AvroConnection(this.getConfigurator(), serviceName);
	}

	/**
	 * @param <T> the target type will unmarshal to
	 * @param input the input stream
	 * @param unmarshaller the unmarshaller to unmarshal output to object
	 * @return the response
	 * @throws RemoteException if fail to read from stream or wrong
	 */
	private <T> Response getResponse(
			final InputStream input, final Unmarshaller<T> unmarshaller) throws RemoteException {
		
		// get production or development mode from stream
		int mode;
		try {
			mode = input.read();
		} catch (IOException e) {
			throw new RemoteException("Fail to read data from remote server", e);
		}
		
		// to check whether server return same mode as client (production or development)
		if (this.isProductionMode()) {
			if (mode != Constants.PRODUCTION_MODE) {
				throw new RemoteException("Server does not response that works in production mode");
			}
		} else {
			if (mode != Constants.DEVELOPMENT_MODE) {
				throw new RemoteException("Server does not response that works in development mode");
			}
		}
		
		// get action from server stream
		int action;
		try {
			action = input.read();
		} catch (IOException e) {
			throw new RemoteException("Fail to read data from remote server", e);
		}
		
		switch (action) {
			
			case Constants.RESPONSE.LOGGED_IN:
				LoggedResponse logged = new LoggedResponse(this);
				logged.read(input);
				
				this.serverFrameworkVersion = logged.getServerFrameworkVersion();
				this.token = logged.getToken();
				
				return logged;
				
			case Constants.RESPONSE.CANT_LOGGED_IN:
				CantLoggedResponse cantLogged = new CantLoggedResponse(this);
				cantLogged.read(input);
				
				return cantLogged;
				
			case Constants.RESPONSE.LOGGED_OUT:
				return new LogoutResponse();
			
			case Constants.RESPONSE.INVALID_REQUEST:
				CantLoggedResponse invalidRequest = new CantLoggedResponse(this);
				invalidRequest.read(input);
				
				throw new RemoteException(invalidRequest.getMessage());
				
			case Constants.RESPONSE.NOT_LOGGED:
				throw new RemoteException("Client does not logged to server yet, please log in first");
				
			case Constants.RESPONSE.NO_ACCESS_RIGHT:
				throw new RemoteException("Client does not have access right to access this service");
				
			case Constants.RESPONSE.SUCCESS_EXECUTED:

				SuccessExecutedResponse<T> withDataResponse = new SuccessExecutedResponse<T>(this, unmarshaller);
				withDataResponse.read(input);
				
				if (withDataResponse.getToken() != null) {
					this.token = withDataResponse.getToken();
				}
				
				return withDataResponse;
				
			case Constants.RESPONSE.FAIL_EXECUTED:
				
				FailExecutedResponse failExecuteResponse = new FailExecutedResponse(this);
				failExecuteResponse.read(input);
				
				throw new RemoteException(failExecuteResponse.getMessage());
				
			default:
				throw new RemoteException("Invalid action code [{0}] received from remote server", action);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Client#login(java.lang.String, java.lang.String)
	 */
	@Override
	public void login(final String username, final String password) throws RemoteException {
		
		// send log in request to server with user name and password
		Connection connection = this.getConnection(this.getConfigurator().getLoginURL());
		Request request = new LoginRequest(this, username, password);
		request.write(connection.getOutputStream());
		
		// process server response
		Response response = this.getResponse(connection.getInputStream(), null);
		switch (response.getCode()) {
			case Constants.RESPONSE.LOGGED_IN:
				return;
				
			case Constants.RESPONSE.CANT_LOGGED_IN:
				throw new RemoteException(((CantLoggedResponse) response).getMessage());
				
			default:
				throw new RemoteException("Do not know why server response with code [{0}]", response.getCode());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Client#logout()
	 */
	@Override
	public void logout() throws RemoteException {
		
		Connection connection = this.getConnection(this.getConfigurator().getLoginURL());
		new LogoutRequest(this).write(connection.getOutputStream());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Client#getContext(java.lang.Class, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <S, T> Context<S, T> getContext(final Class<S> sourceType, final Class<T> targetType) {
		
		Marshaller<S> marshaller = null;
		if (sourceType != null) {
		marshaller = (Marshaller<S>) this.marshallers.get(sourceType);
			if (marshaller == null) {
				marshaller = new AvroMarshallerFactory().create(sourceType);
				this.marshallers.put(sourceType, marshaller);
			}
		}
		
		Unmarshaller<T> unmarshaller = null;
		if (targetType != null) {
			unmarshaller = (Unmarshaller<T>) this.unmarshallers.get(targetType);
			if (unmarshaller == null) {
				unmarshaller = new AvroUnmarshallerFactory().create(targetType);
			}
		}
		
		return new AvroContext<S, T>(marshaller, unmarshaller);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Client#execute(java.lang.String, com.corona.remote.Context, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <S, T> T execute(final String name, final Context<S, T> context, final S data) throws RemoteException {
		
		Connection connection = this.getConnection(this.getConfigurator().getLoginURL());
		ExecutionRequest<S> request = new ExecutionRequest<S>(this, context, data);
		request.write(connection.getOutputStream());

		// process server response
		Response response = this.getResponse(connection.getInputStream(), context.getUnmarshaller());
		switch (response.getCode()) {
			
			case Constants.RESPONSE.SUCCESS_EXECUTED:
				return ((SuccessExecutedResponse<T>) response).getValue();
				
			default:
				throw new RemoteException("Do not know why server response with code [{0}]", response.getCode());
		}
	}
}
