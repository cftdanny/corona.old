/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.corona.io.Unmarshaller;

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
	 * @param unmarshaller the unmarshaller
	 * @return the response from server
	 * @exception RemoteException if fail to read server data to response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static ClientResponse getResponse(
			final Client client, final Connection connection, final Unmarshaller unmarshaller) throws RemoteException {

		// open input stream from server in order to read data sent by server
		InputStream input = connection.getInputStream();
		
		// check whether can read identifier and it is defined identifier 
		int identifier;
		try {
			identifier = input.read();
			if (identifier == -1) {
				throw new RemoteException("Should can read identifier from server input stream but it is empty");
			} else if (((byte) identifier) != Constants.IDENTIFIER) {
				
				String source = getStreamAsString(input, identifier);
				throw new RemoteException(
						"Invalid identifier [{0}] from server input stream, source: {1}", (byte) identifier, source
				);
			}
		} catch (IOException e) {
			throw new RemoteException("Fail to read identifier from server input stream", e);
		}
		
		// read action code from server
		int action;
		try {
			action = input.read();
			if (action == -1) {
				throw new RemoteException("Should can read action code from server input stream but it is empty");
			}
		} catch (IOException e) {
			throw new RemoteException("Fail to read action code from server input stream", e);
		}
		
		// create response according to action code
		ClientResponse response;
		switch (action) {
			
			case Constants.RESPONSE.SUCCESS_EXECUTED:
				response = new ClientExecutedResponse(client, unmarshaller);
				break;
				
			case Constants.RESPONSE.FAIL_EXECUTED:
				response = new ClientFailExecuteResponse(client);
				break;
				
			case Constants.RESPONSE.INTERNAL_ERROR:
				response = new ClientInternalErrorResponse(client);
				break;
			
			case Constants.RESPONSE.LOGGED_IN:
				response = new ClientLoggedInResponse(client);
				break;
			
			case Constants.RESPONSE.CANT_LOGGED_IN:
				response = new ClientCantLoggedInResponse(client);
				break;
				
			case Constants.RESPONSE.LOGGED_OUT:
				response = new ClientLogoutResponse();
				break;
				
			case Constants.RESPONSE.INVALID_REQUEST:
				response = new ClientInvalidActionResponse(client);
				break;
				
			default:
				throw new RemoteException(
						"Invalid action [{0}], source: {1}", action, getStreamAsString(input, identifier, action)
				);
		}
		
		// read response data from client input stream
		response.read(input);
		
		return response;
	}
	
	/**
	 * @param input the input stream
	 * @param data the data read before
	 * @return the string
	 * @exception RemoteException if fail to read server data from stream
	 */
	private static String getStreamAsString(final InputStream input, final int... data) throws RemoteException {
		
		ByteArrayOutputStream array = new ByteArrayOutputStream();
		for (int b : data) {
			array.write(b);
		}
		try {
			for (int b = input.read(); b != -1; b = input.read()) {
				array.write(b);
			}
		} catch (IOException e) {
			throw new RemoteException("Fail to read data from server stream", e);
		}
		return new String(array.toByteArray());
	}
}
