/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>The request will be executed at server </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Request {

	/**
	 * @return the request code
	 */
	byte getCode();
	
	/**
	 * @param out the output stream
	 * @throws IOException if fail to serialize request to output stream
	 */
	void write(OutputStream out) throws IOException;
}
