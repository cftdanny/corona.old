/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.InjectMethod;
import com.corona.context.ValueException;
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
 * <p>This class is used to register a method that is annotated with injection annotation. 
 * Its arguments will be resolved from container before it can be used for others. </p>
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
	 * the annotated property
	 */
	private Method method;

	/**
	 * the factory name for unmarshaller
	 */
	private String name;
	
	/**
	 * the unmarshaller to unmarshal request data
	 */
	@SuppressWarnings("rawtypes")
	private Unmarshaller unmarshaller = null;
	
	/**
	 * the server name
	 */
	private String serverName;
	
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param annotatedMethod the method that is annotated with {@link Remote}
	 */
	RemoteInjectMethod(final ContextManagerFactory contextManagerFactory, final Method annotatedMethod) {
		this.method = annotatedMethod;

		Remote remote = this.method.getAnnotation(Remote.class);
		this.name = remote.value();

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
	@Override
	public Object invoke(final ContextManager contextManager, final Object component) {
		
		// read request object from remote client stream
		Request request = null;
		try {
			request = this.getRequest(contextManager);
		} catch (Exception e) {
			return new ValueException("Fail marshal stream to response object", e);
		}
		
		// execute producing method according to request
		Object outcome = null;
		try {
			switch (request.getCode()) {
				case Request.LOGIN:
					outcome = this.login(component, (LoginRequest) request);
					break;
					
				case Request.LOGOUT:
					outcome = this.logout(component, (LogoutRequest) request);
					break;
					
				default:
					outcome = this.execute(component, (ExecuteRequest) request);
					break;
			}
		} catch (Exception e) {
			return new FailExecuteResponse(e);
		}
		
		// return result to remote producer to send result to remote client
		return new Result(request, outcome);
	}
	
	/**
	 * @return the unmarshaller factory
	 */
	private UnmarshallerFactory getUnmarshallerFactory() {
		return UnmarshallerFactory.get(this.name);
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
	 * @param contextManager the current context manager
	 * @return the request
	 * @throws Exception if fail to translate stream to request object
	 */
	private Request getRequest(final ContextManager contextManager) throws Exception {
		
		InputStream input = contextManager.get(HttpServletRequest.class).getInputStream();
		input = new GZIPInputStream(input);
		
		// read execution mode and check whether execution mode is valid or not
		int identifier = input.read();
		if (identifier == -1) {
			this.logger.error("Should can read execution mode from client stream but can't");
			throw new RemoteException("Should can read execution mode from client stream but can't");
		} else if (((byte) identifier) != Constants.IDENTIFIER) {
			this.logger.error("Invalid execution mode, must be PRODUCTION or DEVELOPMENT");
			throw new RemoteException("Invalid execution mode, must be PRODUCTION or DEVELOPMENT");
		}
		
		// read action code and make sure it is read from client stream
		int action = input.read();
		if (action == -1) {
			this.logger.error("Should can read action code from client stream but can't");
			throw new RemoteException("Should can read action code from client stream but can't");
		}
		
		// resolve server from context and make sure it is defined
		Server server = contextManager.get(Server.class, this.serverName);
		if (server == null) {
			this.logger.error("Server [{0}] can't find, should define it first", this.serverName);
			throw new RemoteException("Server [{0}] can't find, should define it first", this.serverName);
		}
		
		// create request according to action code
		Request request = null;
		switch (action) {
			case Request.EXECUTE:
				request = new ExecuteRequest(server, this.getUnmarshaller());
				break;

			case Request.LOGIN:
				request = new LoginRequest(server);
				break;
			
			case Request.LOGOUT:
				request = new LogoutRequest(server);
				break;
				
			default:
				this.logger.error("Invalid action code, must be EXECUTE, LOGIN or LOGOUT");
				throw new RemoteException("Invalid action code, must be EXECUTE, LOGIN or LOGOUT");
		}
		
		request.read(input);
		return request;
	}
	
	/**
	 * @param component the component
	 * @param request the log in request
	 * @return the outcome after executed method
	 * @throws Exception if fail to execute producing method
	 */
	private Object login(final Object component, final LoginRequest request) throws Exception {
		return this.method.invoke(component, request.getUsername(), request.getPassword());
	}
	
	/**
	 * @param component the component
	 * @param request the log out request
	 * @return the outcome after executed method
	 * @throws Exception if fail to execute producing method
	 */
	private Object logout(final Object component, final LogoutRequest request) throws Exception {
		return this.method.invoke(component, request.getToken());
	}
	
	/**
	 * @param component the component
	 * @param request the executing request
	 * @return the outcome after executed method
	 * @throws Exception if fail to execute producing method
	 */
	private Object execute(final Object component, final ExecuteRequest request) throws Exception {
		
		int size = this.method.getParameterTypes().length;
		if (size == 2) {
			
			// 2 parameters, token and data
			return this.method.invoke(component, request.getToken(), request.getData());
		} else if (size == 1) {
			
			// 1 parameter, token or data
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
