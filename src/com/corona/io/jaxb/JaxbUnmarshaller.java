/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.jaxb;

import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.corona.io.UnmarshalException;

/**
 * <p>This class is used to unmarshal XML into Java object </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the type that input stream will be unmarshalled to
 */
public class JaxbUnmarshaller<T> implements com.corona.io.Unmarshaller<T> {

	/**
	 * the JAXB unmarshaller
	 */
	private Unmarshaller unmarshaller;
	
	/**
	 * @param unmarshaller the JAXB unmarshaller
	 */
	JaxbUnmarshaller(final Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.io.Unmarshaller#unmarshal(java.io.InputStream)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T unmarshal(final InputStream in) throws UnmarshalException {
		
		try {
			return (T) this.unmarshaller.unmarshal(in);
		} catch (JAXBException e) {
			throw new UnmarshalException("Fail to unmarshal input stream into object", e);
		}
	}
}
