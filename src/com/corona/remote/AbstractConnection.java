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
public abstract class AbstractConnection implements Connection {

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
	 * @param configurator the server configuration
	 * @param serviceName the service name
	 * @exception RemoteException if fail to create connection
	 */
	protected AbstractConnection(final Configurator configurator, final String serviceName) throws RemoteException {
		
		try {
			this.connection = new URL(configurator.getBaseURL() + serviceName).openConnection();
		} catch (Exception e) {
			throw new RemoteException("Fail to build connection [{0}/{1}] with service", 
					e, configurator.getBaseURL(), serviceName
			);
		}
		
		this.connection.setDoInput(true);
		this.connection.setDoOutput(true);
		
		try {
			this.inputStream = new GZIPInputStream(this.connection.getInputStream());
			this.outputStream = new GZIPOutputStream(this.connection.getOutputStream());
		} catch (Exception e) {
			throw new RemoteException("Fail to create GZIP stream with connection to server", e);
		} finally {
			
			if (this.inputStream != null) {
				try {
					this.inputStream.close();
				} catch (Exception e) {
					throw new RemoteException("Fail to close input stream", e);
				}
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Connection#getInputStream()
	 */
	@Override
	public InputStream getInputStream() {
		return this.inputStream;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Connection#getOutputStream()
	 */
	@Override
	public OutputStream getOutputStream() {
		return this.outputStream;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Connection#close()
	 */
	@Override
	public void close() throws RemoteException {
		
		try {
			this.inputStream.close();
		} catch (IOException e) {
			throw new RemoteException("Fail to close input stream", e);
		} finally {
			
			try {
				this.outputStream.close();
			} catch (Exception e) {
				throw new RemoteException("Fail to close output stream", e);
			}
		}
	}
}
