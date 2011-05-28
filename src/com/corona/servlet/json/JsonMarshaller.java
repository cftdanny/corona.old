/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.json;

import java.io.OutputStream;

import com.corona.servlet.ProduceException;

/**
 * <p>The JSON marshaller is used to generate JSON content with an object. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface JsonMarshaller {

	/**
	 * <p>Marshal an object to JSON content. The object to be marshalled can not be null. </p>
	 * 
	 * @param out the output stream that JSON content will write to
	 * @param instance the object to be marshaled to JSON
	 * @exception ProduceException if fail to marshal root to JSON
	 */
	void marshal(final OutputStream out, final Object instance) throws ProduceException;
}
