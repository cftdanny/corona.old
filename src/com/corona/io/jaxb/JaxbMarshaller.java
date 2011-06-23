/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.jaxb;

import java.io.OutputStream;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import com.corona.io.MarshalException;
import com.corona.util.StringUtil;

/**
 * <p>Marshal Java object into XML </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the type of Java object to be marshalled
 */
public class JaxbMarshaller<T> implements com.corona.io.Marshaller<T> {

	/**
	 * the JAXB marshaller
	 */
	private Marshaller marshaller;
	
	/**
	 * @param marshaller the JAXB marshaller
	 */
	JaxbMarshaller(final Marshaller marshaller) {
		this.marshaller = marshaller;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.io.Marshaller#marshal(java.io.OutputStream, java.lang.Object)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void marshal(final OutputStream out, final T bean) throws MarshalException {
		
		try {
			if (bean.getClass().isAnnotationPresent(XmlRootElement.class)) {
				this.marshaller.marshal(bean, out);
			} else {
				this.marshaller.marshal(new JAXBElement(
						new QName("", StringUtil.camel(bean.getClass().getSimpleName())), bean.getClass(), bean
				), out);
			}
		} catch (Exception e) {
			throw new MarshalException("Fail to marshal object into xml stream", e);
		}
	}
}
