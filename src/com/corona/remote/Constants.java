/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

/**
 * <p>The constants for request code and response code between server and client </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Constants {

	/**
	 * the framework library version
	 */
	byte LIBRARY_VESION = 10;
	
	/**
	 * the 
	 */
	byte IDENTIFIER = -10;

	/**
	 * <p>All codes for request </p>
	 */
	public interface REQUEST {
	
		/**
		 * send log in request to server
		 */
		byte LOGIN = 1;
		
		/**
		 * send log out request to server
		 */
		byte LOGOUT = 2;
		
		/**
		 * execute service in server
		 */
		byte EXECUTE = 3;
	}
	
	/**
	 * <p>All codes from response </p>
	 */
	public interface RESPONSE {
		
		/**
		 * user has been logged in
		 */
		byte LOGGED_IN = 1;
		
		/**
		 * fail to logged in server
		 */
		byte CANT_LOGGED_IN = 2;
		
		/**
		 * client is logged out from server
		 */
		byte LOGGED_OUT = 3;
		
		/**
		 * the request is invalid
		 */
		byte INVALID_REQUEST = 4;
		
		/**
		 * client is not logged to server
		 */
		byte NOT_LOGGED = 5;
		
		/**
		 * don't have access right to current request
		 */
		byte NO_ACCESS_RIGHT = 6;
		
		/**
		 * execute OK and return data
		 */
		byte SUCCESS_EXECUTED = 11;

		/**
		 * fail to execute and with error message
		 */
		byte FAIL_EXECUTED = 12;
		
		/**
		 * server internal error with error message
		 */
		byte INTERNAL_ERROR = 13;
	}
}
