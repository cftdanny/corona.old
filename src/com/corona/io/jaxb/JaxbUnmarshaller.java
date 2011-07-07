/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.jaxb;

import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;

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
	 * the unmarshalling to type
	 */
	private Class<T> type;
	
	/**
	 * @param unmarshaller the JAXB unmarshaller
	 * @param type the unmarshalling to type
	 */
	JaxbUnmarshaller(final Unmarshaller unmarshaller, final Class<T> type) {
		this.unmarshaller = unmarshaller;
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.io.Unmarshaller#unmarshal(java.io.InputStream)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T unmarshal(final InputStream in) throws UnmarshalException {
		
		try {
			if (this.type.isAnnotationPresent(XmlRootElement.class)) {
				return (T) this.unmarshaller.unmarshal(in);
			} else {
				return this.unmarshaller.unmarshal(new StreamSource(in), type).getValue();
			}
		} catch (JAXBException e) {
			throw new UnmarshalException("Fail to unmarshal input stream into object", e);
		}
	}
}
