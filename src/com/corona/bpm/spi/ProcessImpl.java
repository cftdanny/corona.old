/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.spi;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ProcessImpl extends Process {

	/**
	 * {@inheritDoc}
	 * @see java.lang.Process#getOutputStream()
	 */
	@Override
	public OutputStream getOutputStream() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Process#getInputStream()
	 */
	@Override
	public InputStream getInputStream() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Process#getErrorStream()
	 */
	@Override
	public InputStream getErrorStream() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Process#waitFor()
	 */
	@Override
	public int waitFor() throws InterruptedException {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Process#exitValue()
	 */
	@Override
	public int exitValue() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Process#destroy()
	 */
	@Override
	public void destroy() {
	}

}
