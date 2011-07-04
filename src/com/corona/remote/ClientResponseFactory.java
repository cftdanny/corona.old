/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
	static ClientResponse getResponse(final Client client, final Connection connection) throws RemoteException {

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
		switch (action) {
			
			default:
				throw new RemoteException(
						"Invalid action [{0}], source: {1}", identifier, getStreamAsString(input, identifier, action)
				);
		}
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
