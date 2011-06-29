/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import com.corona.io.avro.AvroUnmarshallerFactory;

/**
 * <p>This factory is used to create {@link Unmarshaller} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class UnmarshallerFactory {
	
	/**
	 * all unmarshaller factories
	 */
	private static Map<String, UnmarshallerFactory> factories = null;

	/**
	 * @return the factory name
	 */
	public abstract String getName();

	/**
	 * @param <T> the type that input stream will be unmarshalled to
	 * @param type the class type
	 * @return the unmarshaller
	 */
	public abstract <T> Unmarshaller<T> create(Class<T> type);

	/**
	 * @param <T> the type that input stream will be unmarshalled to
	 * @param type the class type
	 * @param context the unmarshalling context
	 * @return the unmarshaller
	 */
	public abstract <T> Unmarshaller<T> create(Class<T> type, Map<String, Object> context);

	/**
	 * @return the default marshaller
	 */
	public static final UnmarshallerFactory get() {
		return get(AvroUnmarshallerFactory.NAME);
	}

	/**
	 * @param name the name of marshaller factory 
	 * @return the resolved marshaller factory or <code>null</code> if it isn't registered
	 */
	public static final UnmarshallerFactory get(final String name) {
		
		if (factories == null) {
			
			factories = new HashMap<String, UnmarshallerFactory>();
			for (UnmarshallerFactory factory : ServiceLoader.load(UnmarshallerFactory.class)) {
				factories.put(factory.getName(), factory);
			}
		}
		return factories.get(name);
	}
}
