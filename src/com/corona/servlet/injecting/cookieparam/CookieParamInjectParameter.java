/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.cookieparam;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

import com.corona.component.cookie.CookieManager;
import com.corona.context.AbstractInjectParameter;
import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.CookieParam;
import com.corona.util.ConvertUtil;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class CookieParamInjectParameter extends AbstractInjectParameter {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(CookieParamInjectParameter.class);

	/**
	 * the component name
	 */
	private String name = null;

	/**
	 * @param accessible the constructor or method that parameter exists in
	 * @param parameterType the class type of annotated parameter
	 * @param annotations all annotations for parameter
	 */
	CookieParamInjectParameter(
			final AccessibleObject accessible, final Class<?> parameterType, final Annotation[] annotations
	) {
		
		super(accessible, parameterType, annotations);
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(CookieParam.class)) {
				this.name = ((CookieParam) annotation).value();
				break;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectParameter#get(com.corona.context.ContextManager)
	 */
	@Override
	public Object get(final ContextManager contextManager) {
		
		// resolve match result from current context manager
		CookieManager cookieManager = contextManager.get(CookieManager.class);
		if (cookieManager == null) {
			this.logger.error("Request does not run at HTTP SERVLET context, can not find cookie");
			throw new ConfigurationException(
					"Request does not run at HTTP SERVLET context, can not find cookie"
			);
		}
		
		// get value from match result by parameter name
		Object value = cookieManager.getValue(this.name);
		if ((value == null) && (!this.isOptional())) {
			this.logger.error(
					"Parameter [{0}] in constructor or method [{1}] value is mandatory, but cookie does not exist", 
					this.getType(), this.getAccessible()
			);
			throw new ValueException(
					"Parameter [{0}] in constructor or method [{1}] value is mandatory, but cookie does not exist", 
					this.getType(), this.getAccessible()
			);
		}
		
		// if parameter value is String, will convert to expected type as field
		if ((value != null) && String.class.equals(value.getClass())) {
			return ConvertUtil.getAsType((String) value, this.getType());
		}
		return value;
	}
}
