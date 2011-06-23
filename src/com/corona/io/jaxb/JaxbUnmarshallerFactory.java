/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.jaxb;

import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.corona.io.Unmarshaller;
import com.corona.io.UnmarshallerFactory;

/**
 * <p>This factory is used to create {@link JacksonUnmarshaller} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class JaxbUnmarshallerFactory implements UnmarshallerFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.io.UnmarshallerFactory#create(java.lang.Class)
	 */
	@Override
	public <T> Unmarshaller<T> create(final Class<T> type) {
		
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(type);
			return new JaxbUnmarshaller<T>(context.createUnmarshaller());
		} catch (JAXBException e) {
			throw new IllegalArgumentException("Fail to create JAXB context or unmarshaller", e);
		}
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
