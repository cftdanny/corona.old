/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import com.corona.remote.Constants;
import com.corona.remote.Server;

/**
 * <p>This response is used to send message to client that fail to log in server </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ServerCantLoggedInResponse extends AbstractServerResponse {

	/**
	 * @param server the server
	 */
	ServerCantLoggedInResponse(final Server server) {
		super(server);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.producing.remote.AbstractServerResponse#getCode()
	 */
	@Override
	protected byte getCode() {
		return Constants.RESPONSE.CANT_LOGGED_IN;
	}
}
