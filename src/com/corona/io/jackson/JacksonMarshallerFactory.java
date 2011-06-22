/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.jackson;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.corona.io.Marshaller;
import com.corona.io.MarshallerFactory;

/**
 * <p>This factory is used to create {@link JacksonMarshaller} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class JacksonMarshallerFactory implements MarshallerFactory {

	/**
	 * the jackson object mapper
	 */
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.io.MarshallerFactory#create(java.lang.Class)
	 */
	@Override
	public <T> Marshaller<T> create(final Class<T> type) {
		return new JacksonMarshaller<T>(MAPPER);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.io.MarshallerFactory#create(java.lang.Class, java.util.Map)
	 */
	@Override
	public <T> Marshaller<T> create(final Class<T> type, final Map<String, Object> context) {
		return this.create(type);
	}
}
