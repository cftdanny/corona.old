/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.json;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.AbstractInjectField;
import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.io.Unmarshaller;
import com.corona.io.UnmarshallerFactory;
import com.corona.io.jackson.JacksonUnmarshallerFactory;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This factory is used to create {@link JsonRequestInjector} by {@link JsonRequest} annotation </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class JsonRequestInjectField extends AbstractInjectField {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(JsonRequestInjectField.class);
	
	/**
	 * the unmarshaller to unmarshal JSON request
	 */
	@SuppressWarnings("rawtypes")
	private Unmarshaller unmarshaller;

	/**
	 * @param field the field that is annotated with an annotation type
	 */
	JsonRequestInjectField(final Field field) {
		super(field);
		this.unmarshaller = UnmarshallerFactory.get(JacksonUnmarshallerFactory.NAME).create(
				field.getType()
		);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractInjectField#get(com.corona.context.ContextManager)
	 */
	@Override
	protected Object get(final ContextManager contextManager) {
		
		HttpServletRequest request = contextManager.get(HttpServletRequest.class);
		try {
			return this.unmarshaller.unmarshal(request.getInputStream());
		} catch (Exception e) {
			
			this.logger.error("Fail to unmarshal request URL [{0}] by JSON unmarshaller", e, request.getPathInfo());
			throw new ValueException(
					"Fail to unmarshal request URL [{0}] by JSON unmarshaller", e, request.getPathInfo()
			);
		}
	}
}