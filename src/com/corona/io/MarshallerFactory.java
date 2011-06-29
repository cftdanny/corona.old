/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import com.corona.io.avro.AvroMarshallerFactory;

/**
 * <p>This factory is used to create {@link Marshaller} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class MarshallerFactory {

	/**
	 * all marshaller factories
	 */
	private static Map<String, MarshallerFactory> factories = null;
	
	/**
	 * @return the factory name
	 */
	public abstract String getName();
	
	/**
	 * @param <T> the type of Java object to be marshalled
	 * @param type the class type
	 * @return the marshaller
	 */
	public abstract <T> Marshaller<T> create(Class<T> type);

	/**
	 * @param <T> the type of Java object to be marshalled
	 * @param type the class type
	 * @param context the context for mashalling
	 * @return the marshaller
	 */
	public abstract <T> Marshaller<T> create(Class<T> type, Map<String, Object> context);
	
	/**
	 * @return the default marshaller
	 */
	public static final MarshallerFactory get() {
		return get(AvroMarshallerFactory.NAME);
	}

	/**
	 * @param name the name of marshaller factory 
	 * @return the resolved marshaller factory or <code>null</code> if it isn't registered
	 */
	public static final MarshallerFactory get(final String name) {
		
		if (factories == null) {
			
			factories = new HashMap<String, MarshallerFactory>();
			for (MarshallerFactory factory : ServiceLoader.load(MarshallerFactory.class)) {
				factories.put(factory.getName(), factory);
			}
		}
		return factories.get(name);
	}
}
