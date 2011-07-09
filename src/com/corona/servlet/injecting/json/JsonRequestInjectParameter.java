/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.json;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.AbstractInjectParameter;
import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.io.Unmarshaller;
import com.corona.io.UnmarshallerFactory;
import com.corona.io.jackson.JacksonUnmarshallerFactory;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This injector is used to inject JSON request into a parameter in method </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class JsonRequestInjectParameter extends AbstractInjectParameter {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(JsonRequestInjectParameter.class);

	/**
	 * the unmarshaller to unmarshal JSON request
	 */
	@SuppressWarnings("rawtypes")
	private Unmarshaller unmarshaller;
	
	/**
	 * @param accessible the constructor or method that parameter exists in
	 * @param parameterType the class type of annotated parameter
	 * @param annotations all annotations for parameter
	 */
	JsonRequestInjectParameter(
			final AccessibleObject accessible, final Class<?> parameterType, final Annotation[] annotations
	) {
		super(accessible, parameterType, annotations);
		this.unmarshaller = UnmarshallerFactory.get(JacksonUnmarshallerFactory.NAME).create(parameterType);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectParameter#get(com.corona.context.ContextManager)
	 */
	@Override
	public Object get(final ContextManager contextManager) {
		
		HttpServletRequest request = contextManager.get(HttpServletRequest.class);
		try {
			return this.unmarshaller.unmarshal(request.getInputStream());
		} catch (Exception e) {
			
			this.logger.error(
					"Fail to unmarshal request URL [{0}] in constructor or method [{1}] by JSON unmarshaller", 
					e, request.getPathInfo(), this.getAccessible()
			);
			throw new ValueException(
					"Fail to unmarshal request URL [{0}] in constructor or method [{1}] by JSON unmarshaller", 
					e, request.getPathInfo(), this.getAccessible()
			);
		}
	}
}
