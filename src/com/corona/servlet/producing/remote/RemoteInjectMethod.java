/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.InjectMethod;
import com.corona.context.ValueException;
import com.corona.io.Marshaller;
import com.corona.io.MarshallerFactory;
import com.corona.io.Unmarshaller;
import com.corona.io.UnmarshallerFactory;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.remote.Constants;
import com.corona.remote.RemoteException;
import com.corona.remote.Server;
import com.corona.servlet.annotation.Remote;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to execute method that is annotated with {@link Remote}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class RemoteInjectMethod implements InjectMethod {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(RemoteInjectMethod.class);
	
	/**
	 * the annotated method with @Remote
	 */
	private Method method;
	
	/**
	 * the server name
	 */
	private String serverName;

	/**
	 * the factory name for marshaller and unmarshaller
	 */
	private String protocol;

	/**
	 * the marshaller to marshal response data
	 */
	@SuppressWarnings("rawtypes")
	private Marshaller marshaller = null;
	
	/**
	 * the unmarshaller to unmarshal request data
	 */
	@SuppressWarnings("rawtypes")
	private Unmarshaller unmarshaller = null;
	
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param annotatedMethod the method that is annotated with {@link Remote}
	 */
	RemoteInjectMethod(final ContextManagerFactory contextManagerFactory, final Method annotatedMethod) {
		this.method = annotatedMethod;

		// find marshaller and unmarshaller protocol name
		Remote remote = this.method.getAnnotation(Remote.class);
		this.protocol = remote.value();

		// find server name, if name is blank, make it empty (null)
		this.serverName = remote.server();
		if (StringUtil.isBlank(this.serverName)) {
			this.serverName = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectMethod#getMethod()
	 */
	@Override
	public Method getMethod() {
		return this.method;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectMethod#invoke(com.corona.context.ContextManager, java.lang.Object)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object invoke(final ContextManager contextManager, final Object component) {
		
		// find server by server name from current context and make sure it is defined
		Server server = contextManager.get(Server.class, this.serverName);
		if (server == null) {
			this.logger.error("Server [{0}] isn't registered in context, should define it first", this.serverName);
			throw new ValueException(
					"Server [{0}] isn't registered in context, should define it first", this.serverName
			);
		}

		// transform client input stream into request object
		ServerRequest request = null;
		try {
			request = this.getRequest(contextManager, server);
		} catch (RemoteException e) {
			this.logger.error("Fail to open client input stream or transform stream to server request object", e);
			return new ServerInternalErrorResponse(server, "Fail to get server request object: " + e.getMessage());
		}
		
		// execute producing method according to client request and return it in order to create server response
		try {
			switch (request.getCode()) {
				case Constants.REQUEST.EXECUTE:
					Object outcome = this.execute(component, (ServerExecuteRequest) request);
					if (outcome != null) {
						return new ServerExecutedResponse(server, this.getMarshaller(), outcome);
					} else {
						return new ServerExecutedResponse(server, null, null);
					}
					
				case Constants.REQUEST.LOGIN:
					String token = this.login(component, (ServerLoginRequest) request);
					if (token != null) {
						return new ServerLoggedInResponse(server, token);
					} else {
						return new ServerCantLoggedInResponse(server);
					}
					
				default:
					this.logout(component, (ServerLogoutRequest) request);
					return new ServerLoggedOutResponse(server);
			}
		} catch (Exception e) {
			return new ServerFailExecuteResponse(server, e);
		}
	}
	
	/**
	 * @return the unmarshaller factory
	 */
	private UnmarshallerFactory getUnmarshallerFactory() {
		return UnmarshallerFactory.get(this.protocol);
	}
	
	/**
	 * @return the unmarshaller to unmarshal request stream
	 */
	@SuppressWarnings("rawtypes")
	private Unmarshaller getUnmarshaller() {

		if (this.unmarshaller == null) {
			
			if (this.method.getParameterTypes().length == 2) {
				this.unmarshaller = this.getUnmarshallerFactory().create(this.method.getParameterTypes()[1]);
			} else if (this.method.getParameterTypes().length == 1) {
	
				if (!this.method.getParameterTypes()[0].equals(String.class)) {
					this.unmarshaller = getUnmarshallerFactory().create(this.method.getParameterTypes()[0]);
				}
			}
		}
		return this.unmarshaller;
	}
	
	/**
	 * @return the marshaller that is used to marshal response data
	 */
	@SuppressWarnings("rawtypes")
	private Marshaller getMarshaller() {
		
		if (this.marshaller != null) {
			this.marshaller = MarshallerFactory.get(this.protocol).create(this.method.getReturnType());
		}
		return this.marshaller;
	}
	
	/**
	 * @param contextManager the current context manager
	 * @param server the server
	 * @return the request
	 * @throws RemoteException if fail to translate stream to request object
	 */
	private ServerRequest getRequest(final ContextManager contextManager, final Server server) throws RemoteException {
		
		// get input stream from 
		InputStream input;
		try {
			input = contextManager.get(HttpServletRequest.class).getInputStream();
			input = new GZIPInputStream(input);
		} catch (IOException e) {
			this.logger.error("Fail to open client servlet request as GZIP input stream", e);
			throw new RemoteException("Fail to open client servlet request as GZIP input stream", e);
		}
		
		// read identifier and check whether this identifier is valid or not
		int identifier;
		try {
			identifier = input.read();
		} catch (IOException e) {
			this.logger.error("Fail to read identifier from client input stream", e);
			throw new RemoteException("Fail to read identifier from client input stream", e);
		}
		
		if (identifier == -1) {
			this.logger.error("Should can read identifier but client input stream is empty");
			throw new RemoteException("Should can read identifier but client input stream is empty");
		} else if (((byte) identifier) != Constants.IDENTIFIER) {
			this.logger.error("Invalid identifier [{0}] read from client input stream", identifier);
			throw new RemoteException("Invalid identifier [{0}] read from client input stream", identifier);
		}
		
		// read action code and make sure it is read from client stream
		int action;
		try {
			action = input.read();
		} catch (IOException e) {
			this.logger.error("Fail to read action code from client input stream", e);
			throw new RemoteException("Fail to read action code from client input stream", e);
		}
		
		if (action == -1) {
			this.logger.error("Should can read action code but client input stream is empty");
			throw new RemoteException("Should can read action code but client input stream is empty");
		}
		
		// create request according to action code
		ServerRequest request;
		switch (action) {
			case Constants.REQUEST.EXECUTE:
				request = new ServerExecuteRequest(server, this.getUnmarshaller());
				break;

			case Constants.REQUEST.LOGIN:
				request = new ServerLoginRequest(server);
				break;
			
			case Constants.REQUEST.LOGOUT:
				request = new ServerLogoutRequest(server);
				break;
				
			default:
				this.logger.error("Invalid action code [{0}], must be EXECUTE, LOGIN or LOGOUT", action);
				throw new RemoteException("Invalid action code [{0}], must be EXECUTE, LOGIN or LOGOUT", action);
		}
		
		// read request specified data from client input stream
		request.read(input);
		return request;
	}
	
	/**
	 * @param component the component
	 * @param request the log in request
	 * @return the outcome after executed method
	 * @throws Exception if fail to execute producing method
	 */
	private String login(final Object component, final ServerLoginRequest request) throws Exception {
		return (String) this.method.invoke(component, request.getUsername(), request.getPassword());
	}
	
	/**
	 * @param component the component
	 * @param request the log out request
	 * @throws Exception if fail to execute producing method
	 */
	private void logout(final Object component, final ServerLogoutRequest request) throws Exception {
		this.method.invoke(component, request.getToken());
	}
	
	/**
	 * @param component the component
	 * @param request the executing request
	 * @return the outcome after executed method
	 * @throws Exception if fail to execute producing method
	 */
	private Object execute(final Object component, final ServerExecuteRequest request) throws Exception {
		
		int size = this.method.getParameterTypes().length;
		if (size == 2) {
			
			// 2 parameters, token and data
			return this.method.invoke(component, request.getToken(), request.getData());
		} else if (size == 1) {
			
			// 1 parameter, token or data, check by whether parameter is String
			if (this.method.getParameterTypes()[0].equals(String.class)) {
				return this.method.invoke(component, request.getToken());
			} else {
				return this.method.invoke(component, request.getData());
			}
		} else {
			
			// no parameter
			return this.method.invoke(component);
		}
	}
}
