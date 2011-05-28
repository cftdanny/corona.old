/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.xml;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.corona.context.ContextManager;
import com.corona.context.CreationException;
import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.AbstractProducer;
import com.corona.servlet.ProduceException;

/**
 * <p>This producer is used to create HTTP response by outcome from method annotated with Xml. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class XmlProducer extends AbstractProducer {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(XmlProducer.class);
	
	/**
	 * the JAXB marshaller
	 */
	private Marshaller marshaller;
	
	/**
	 * @param key the component key
	 * @param method the annotated producer method
	 */
	public XmlProducer(final Key<?> key, final DecoratedMethod method) {
		
		super(key, method);
		try {
			JAXBContext context = JAXBContext.newInstance(method.getMethod().getReturnType());
			this.marshaller = context.createMarshaller();
		} catch (Exception e) {
			this.logger.error("Fail to create JAXB marshaller for method [{0}]", e, method);
			throw new CreationException("Fail to create JAXB marshaller for method [{0}]", e, method);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Producer#produce(
	 * 	com.corona.context.ContextManager, javax.servlet.http.HttpServletResponse, java.io.OutputStream, 
	 * 	java.lang.Object, java.lang.Object
	 * )
	 */
	@Override
	public void produce(
			final ContextManager contextManager, final HttpServletResponse response, final OutputStream out,
			final Object component, final Object data) throws ProduceException {
		
		// set content type if it is not set yet
		if (response.getContentType() == null) {
			response.setContentType("application/xml");
		}

		// marshal root object into XML
		try {
			this.marshaller.marshal(data, out);
		} catch (JAXBException e) {
			this.logger.error("Fail to marshal method outcome instance to xml", e);
			throw new ProduceException("Fail to marshal method outcome instance to xml", e);
		}
	}
}
