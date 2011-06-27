/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import com.corona.io.Marshaller;
import com.corona.io.Unmarshaller;

/**
 * <p>This class is used to convert stream from and to object for specified client </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <S> the source type
 * @param <T> the target type
 */
public interface Context<S, T> {

	/**
	 * @return the marshaller that is used to marshal data sent to server
	 */
	Marshaller<S> getMarshaller();
	
	/**
	 * @return the unmarshaller that is used to unmarshaller data received from server
	 */
	Unmarshaller<T> getUnmarshaller();
}
