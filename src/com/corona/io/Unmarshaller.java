/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io;

import java.io.InputStream;

/**
 * <p>The marshaller is used to marshal an Java object into output stream. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the type that input stream will be unmarshalled to
 */
public interface Unmarshaller<T> {

	/**
	 * <p>Unmarshal input stream to Java Object. </p>
	 * 
	 * @param in the input stream
	 * @return the unmarshalled to Java object
	 * @throws UnmarshalException if fail to unmarshal output stream to Java object
	 */
	T unmarshal(final InputStream in) throws UnmarshalException;
}
