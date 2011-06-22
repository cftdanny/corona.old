/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.jackson;

import java.io.InputStream;

import org.codehaus.jackson.map.ObjectMapper;

import com.corona.io.UnmarshalException;
import com.corona.io.Unmarshaller;

/**
 * <p>This unmarshaller is used to unmarshal input stream to an Java object. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the type that input stream will be unmarshalled to
 */
public class JacksonUnmarshaller<T> implements Unmarshaller<T> {

	/**
	 * the jackson object mapper
	 */
	private ObjectMapper mapper;
	
	/**
	 * the class type
	 */
	private Class<T> type;
	
	/**
	 * @param mapper the jackson object mapper
	 * @param type the class type
	 */
	JacksonUnmarshaller(final ObjectMapper mapper, final Class<T> type) {
		this.mapper = mapper;
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.io.Unmarshaller#unmarshal(java.io.InputStream)
	 */
	@Override
	public T unmarshal(final InputStream in) throws UnmarshalException {
		
		try {
			return this.mapper.readValue(in, this.type);
		} catch (Exception e) {
			throw new UnmarshalException("Fail to unmarshall to input stream", e);
		}
	}
}
