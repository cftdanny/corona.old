/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import javax.servlet.http.HttpServletRequest;

import com.corona.servlet.ProduceException;

/**
 * <p>The class that is used to convert request stream </p>
 *
 * @author $Author$
 * @version $Id$
 */
class RemoteRequest {

	static final byte LOGIN = 1;
	
	static final byte LOGOUT = 2;
	
	static final byte EXECUTE = 3;
	
	private byte code;
	
	/**
	 * @param request the HTTP SERVLET request
	 * @throws ProduceException if fail to convert stream to object 
	 */
	RemoteRequest(final HttpServletRequest request) throws ProduceException {
		
	}
}
