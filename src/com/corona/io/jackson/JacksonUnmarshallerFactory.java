/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.jackson;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.corona.io.Unmarshaller;
import com.corona.io.UnmarshallerFactory;

/**
 * <p>This factory is used to create {@link JacksonUnmarshaller} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class JacksonUnmarshallerFactory implements UnmarshallerFactory {

	/**
	 * the jackson object mapper
	 */
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * {@inheritDoc}
	 * @see com.corona.io.UnmarshallerFactory#create(java.lang.Class)
	 */
	@Override
	public <T> Unmarshaller<T> create(final Class<T> type) {
		return new JacksonUnmarshaller<T>(MAPPER, type);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.io.UnmarshallerFactory#create(java.lang.Class, java.util.Map)
	 */
	@Override
	public <T> Unmarshaller<T> create(final Class<T> type, final Map<String, Object> context) {
		return this.create(type);
	}
}
