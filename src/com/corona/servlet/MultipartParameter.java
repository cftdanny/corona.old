/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.io.IOException;

/**
 * <p>This parameter is used to indicate parameter in multipart request </p>
 *
 * @author $Author$
 * @version $Id$
 */
abstract class MultipartParameter {

	/**
	 * the parameter name
	 */
	private String name;

	/**
	 * @param name the parameter name
	 */
	public MultipartParameter(final String name) {
		this.name = name;
	}

	/**
	 * @return the parameter name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param data the data
	 * @param start the start position in data for this parameter
	 * @param length the length in data for this parameter
	 * @throws IOException if fail to append data
	 */
	public abstract void appendData(byte[] data, int start, int length) throws IOException;
}
