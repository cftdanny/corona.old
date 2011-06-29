/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

/**
 * <p>The remote server that to handle request from remote client and produce response to remote client </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Server {

	/**
	 * the server configuration
	 */
	private ServerConfiguration configuration;
	
	/**
	 * @return the server configuration
	 */
	public ServerConfiguration getConfiguration() {
		return this.configuration;
	}
	
	/**
	 * @param configuration the server configuration
	 */
	public void setConfiguration(final ServerConfiguration configuration) {
		this.configuration = configuration;
	}
}
