/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.cookieparam;

import java.lang.reflect.Field;

import com.corona.component.cookie.CookieManager;
import com.corona.context.AbstractInjectField;
import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.CookieParam;
import com.corona.util.ConvertUtil;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class CookieParamInjectField extends AbstractInjectField {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(CookieParamInjectField.class);
	
	/**
	 * the component name
	 */
	private String name;
	
	/**
	 * @param field the field that is annotated with an annotation type
	 */
	CookieParamInjectField(final Field field) {
		
		// construct super class and get parameter name
		super(field);
		
		this.name = field.getAnnotation(CookieParam.class).value();
		if (StringUtil.isBlank(this.name)) {
			this.name = field.getName();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractInjectField#get(com.corona.context.ContextManager)
	 */
	@Override
	protected Object get(final ContextManager contextManager) {
		
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
					"Field [{0}] value is mandatory, but cookie [{1}] does not exist", this.getField(), this.name
			);
			throw new ValueException(
					"Field [{0}] value is mandatory, but cookie [{1}] does not exist", this.getField(), this.name
			);
		}
		
		// if parameter value is String, will convert to expected type as field
		if ((value != null) && String.class.equals(value.getClass())) {
			return ConvertUtil.getAsType((String) value, this.getType());
		}
		return value;
	}
}
