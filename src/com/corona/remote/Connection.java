/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <p>The helper class for connection between server and client </p>
 *
 * @author $Author$
 * @version $Id$
 */
class Connection {

	/**
	 * the URL connection between server and client
	 */
	private URLConnection connection = null;
	
	/**
	 * the input stream to receive data from server
	 */
	private InputStream inputStream = null;
	
	/**
	 * the output stream to send data to server
	 */
	private OutputStream outputStream = null;
	
	/**
	 * @param config the server configuration
	 * @param serviceName the service name
	 * @exception RemoteException if fail to create connection
	 */
	Connection(final Configuration config, final String serviceName) throws RemoteException {
		
		try {
			this.connection = new URL(config.getBaseURL() + serviceName).openConnection();
		} catch (Exception e) {
			throw new RemoteException("Fail to build connection to service [{0}/{1}] with remote server", 
					e, config.getBaseURL(), serviceName
			);
		}
		
		this.connection.setDoInput(true);
		this.connection.setDoOutput(true);
	}

	/**
	 * @return the input stream
	 * @exception RemoteException if fail to get input stream
	 */
	InputStream getInputStream() throws RemoteException {
		
		if (this.inputStream == null) {
			try {
				this.inputStream = new GZIPInputStream(this.connection.getInputStream());
			} catch (Exception e) {
				throw new RemoteException("Fail to create GZIP input stream with remote server", e);
			}
		}
		return this.inputStream;
	}

	/**
	 * @return the output stream
	 * @exception RemoteException if fail to get output stream
	 */
	OutputStream getOutputStream() throws RemoteException {

		if (this.outputStream == null) {
			try {
				this.outputStream = new GZIPOutputStream(this.connection.getOutputStream());
			} catch (IOException e) {
				throw new RemoteException("Fail to create GZIP output stream with remote server", e);
			}
		}
		return this.outputStream;
	}
}
