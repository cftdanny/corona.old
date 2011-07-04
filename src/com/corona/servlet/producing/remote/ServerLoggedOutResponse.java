/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import com.corona.remote.Constants;
import com.corona.remote.Server;

/**
 * <p>This response is used to send a message to client that it has been logged out </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ServerLoggedOutResponse extends AbstractServerResponse {

	/**
	 * @param server the server
	 */
	ServerLoggedOutResponse(final Server server) {
		super(server);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.AbstractServerResponse#getCode()
	 */
	@Override
	protected byte getCode() {
		return Constants.RESPONSE.LOGGED_OUT;
	}
}
