/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.json;

import java.io.OutputStream;

import com.corona.servlet.ProduceException;

/**
 * <p>This generator is used to generate JSON content with object. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Marshaller {

	/**
	 * <p>Marshal root object to JSON content. </p>
	 * 
	 * @param out the output stream that JSON content will write to
	 * @param root the object to be marshaled to JSON
	 * @exception ProduceException if fail to marshal root to JSON
	 */
	void marshal(final OutputStream out, final Object root) throws ProduceException;
}
