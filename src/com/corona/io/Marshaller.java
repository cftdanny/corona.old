/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io;

import java.io.OutputStream;

/**
 * <p>The marshaller is used to marshal an Java object into output stream. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the type of Java object to be marshalled
 */
public interface Marshaller<T> {

	/**
	 * <p>Marshal Java Object into output stream. </p>
	 * 
	 * @param out the output stream
	 * @param bean the Java object
	 * @throws MarshalException if fail to marshal Java object to output stream
	 */
	void marshal(final OutputStream out, final T bean) throws MarshalException;
}
