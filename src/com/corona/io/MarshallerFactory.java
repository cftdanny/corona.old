/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io;

import java.util.Map;

/**
 * <p>This factory is used to create {@link Marshaller} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface MarshallerFactory {

	/**
	 * @param <T> the type of Java object to be marshalled
	 * @param type the class type
	 * @return the marshaller
	 */
	<T> Marshaller<T> create(Class<T> type);

	/**
	 * @param <T> the type of Java object to be marshalled
	 * @param type the class type
	 * @param context the context for mashalling
	 * @return the marshaller
	 */
	<T> Marshaller<T> create(Class<T> type, Map<String, Object> context);
}
