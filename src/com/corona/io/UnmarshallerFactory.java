/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io;

import java.util.Map;

/**
 * <p>This factory is used to create {@link Unmarshaller} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface UnmarshallerFactory {

	/**
	 * @param <T> the type that input stream will be unmarshalled to
	 * @param type the class type
	 * @return the unmarshaller
	 */
	<T> Unmarshaller<T> create(Class<T> type);

	/**
	 * @param <T> the type that input stream will be unmarshalled to
	 * @param type the class type
	 * @param context the unmarshalling context
	 * @return the unmarshaller
	 */
	<T> Unmarshaller<T> create(Class<T> type, Map<String, Object> context);
}
