/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.xml;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;
import com.corona.context.CreationException;
import com.corona.context.InjectMethod;
import com.corona.context.Key;
import com.corona.io.MarshalException;
import com.corona.io.Marshaller;
import com.corona.io.jaxb.JaxbMarshallerFactory;
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
	@SuppressWarnings("rawtypes")
	private Marshaller marshaller;
	
	/**
	 * @param key the component key
	 * @param method the annotated producer method
	 */
	public XmlProducer(final Key<?> key, final InjectMethod method) {
		
		super(key, method);
		try {
			this.marshaller = new JaxbMarshallerFactory().create(method.getMethod().getReturnType());
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
	@SuppressWarnings({ "unchecked" })
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
			this.marshaller.marshal(out, data);
		} catch (MarshalException e) {
			this.logger.error("Fail to marshal method outcome instance to xml", e);
			throw new ProduceException("Fail to marshal method outcome instance to xml", e);
		}
	}
}
