/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;
import com.corona.context.InjectMethod;
import com.corona.context.Key;
import com.corona.remote.RemoteException;
import com.corona.servlet.AbstractProducer;
import com.corona.servlet.ProduceException;

/**
 * <p>Produce stream for remote client. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class RemoteProducer extends AbstractProducer {

	/**
	 * @param key the component key
	 * @param method the method that is annotated with {@link FreeMaker}
	 */
	public RemoteProducer(final Key<?> key, final InjectMethod method) {
		super(key, method);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Producer#produce(
	 * 	com.corona.context.ContextManager, javax.servlet.http.HttpServletResponse, java.io.OutputStream, 
	 * 	java.lang.Object, java.lang.Object
	 * )
	 */
	@Override
	public void produce(
			final ContextManager contextManager, final HttpServletResponse response, final OutputStream out,
			final Object component, final Object data
	) throws ProduceException {
		
		GZIPOutputStream output = null;
		try {
			output = new GZIPOutputStream(out);
		} catch (IOException e) {
			throw new ProduceException("Fail to open GZIP output stream by servlet output stream", e);
		}
		
		try {
			((ServerResponse) data).write(output);
		} catch (RemoteException e) {
			throw new ProduceException("Fail to marshal response object to client output stream", e);
		} finally {
			
			try {
				output.close();
			} catch (IOException e) {
				throw new ProduceException("Fail to close GZIP client output stream", e);
			}
		}
	}
}
