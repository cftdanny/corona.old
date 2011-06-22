/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.jackson;

import java.io.OutputStream;

import org.codehaus.jackson.map.ObjectMapper;

import com.corona.io.MarshalException;
import com.corona.io.Marshaller;

/**
 * <p>This marshaller is used to marshal Java object to stream by Jackson JSON </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the type of Java object to be marshalled
 */
public class JacksonMarshaller<T> implements Marshaller<T> {

	/**
	 * the jackson object mapper
	 */
	private ObjectMapper mapper;
	
	/**
	 * @param mapper the jackson object mapper
	 */
	JacksonMarshaller(final ObjectMapper mapper) {
		this.mapper = mapper;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.io.Marshaller#marshal(java.io.OutputStream, java.lang.Object)
	 */
	@Override
	public void marshal(final OutputStream out, final T bean) throws MarshalException {
		
		try {
			this.mapper.writeValue(out, bean);
		} catch (Exception e) {
			throw new MarshalException("Fail to marshal object to output stream", e);
		}
	}
}
