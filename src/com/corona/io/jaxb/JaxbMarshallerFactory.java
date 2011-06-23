/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.jaxb;

import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.corona.io.Marshaller;
import com.corona.io.MarshallerFactory;

/**
 * <p>Marshal Java object into XML </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class JaxbMarshallerFactory implements MarshallerFactory {

	/**
	 * {@inheritDoc}
	 * @see com.corona.io.MarshallerFactory#create(java.lang.Class)
	 */
	@Override
	public <T> Marshaller<T> create(final Class<T> type) {
		
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(type);
			return new JaxbMarshaller<T>(context.createMarshaller());
		} catch (JAXBException e) {
			throw new IllegalArgumentException("Fail to create JAXB context or marshaller", e);
		}
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