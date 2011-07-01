/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.io.InputStream;

import com.corona.servlet.ProduceException;

/**
 * <p>The class that is used to convert request stream </p>
 *
 * @author $Author$
 * @version $Id$
 */
interface Request {

	static final byte LOGIN = 1;
	
	static final byte LOGOUT = 2;
	
	static final byte EXECUTE = 3;

	/**
	 * @return the code
	 */
	byte getCode();
	
	void read(InputStream input) throws ProduceException;
}
