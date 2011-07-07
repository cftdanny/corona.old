/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.xml;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.AbstractInjectProperty;
import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.ValueException;
import com.corona.io.Unmarshaller;
import com.corona.io.UnmarshallerFactory;
import com.corona.io.jaxb.JaxbUnmarshallerFactory;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This class is used to register a setter method that is annotated with injection annotation. 
 * Its value will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class XmlRequestInjectProperty extends AbstractInjectProperty {
	
	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(XmlRequestInjectProperty.class);

	/**
	 * the unmarshaller to unmarshal JSON request
	 */
	@SuppressWarnings("rawtypes")
	private Unmarshaller unmarshaller;
 
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param property the property that is annotated with {@link Inject}
	 */
	XmlRequestInjectProperty(final ContextManagerFactory contextManagerFactory, final Method property) {
		
		super(contextManagerFactory, property);
		this.unmarshaller = UnmarshallerFactory.get(JaxbUnmarshallerFactory.NAME).create(
				property.getParameterTypes()[0]
		);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractInjectProperty#get(com.corona.context.ContextManager)
	 */
	@Override
	protected Object get(final ContextManager contextManager) {

		HttpServletRequest request = contextManager.get(HttpServletRequest.class);
		try {
			return this.unmarshaller.unmarshal(request.getInputStream());
		} catch (Exception e) {
			
			this.logger.error("Fail to unmarshal request URL [{0}] by JAXB unmarshaller", e, request.getPathInfo());
			throw new ValueException(
					"Fail to unmarshal request URL [{0}] by JAXB unmarshaller", e, request.getPathInfo()
			);
		}
	}
}
