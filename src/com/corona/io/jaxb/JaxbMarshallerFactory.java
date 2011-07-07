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
public class JaxbMarshallerFactory extends MarshallerFactory {

	/**
	 * the factory name
	 */
	public static final String NAME = "jaxb";
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.io.MarshallerFactory#getName()
	 */
	@Override
	public String getName() {
		return NAME;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.io.MarshallerFactory#create(java.lang.Class)
	 */
	@Override
	public <T> Marshaller<T> create(final Class<T> type) {
		
		try {
			return new JaxbMarshaller<T>(JAXBContext.newInstance(type).createMarshaller());
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
