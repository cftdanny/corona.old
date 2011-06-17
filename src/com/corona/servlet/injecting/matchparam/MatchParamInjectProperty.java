/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.matchparam;

import java.beans.Introspector;
import java.lang.reflect.Method;

import com.corona.context.AbstractInjectProperty;
import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.MatchResult;
import com.corona.servlet.annotation.MatchParam;
import com.corona.servlet.injecting.param.ParamUtil;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to register a setter method that is annotated with injection annotation. 
 * Its value will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class MatchParamInjectProperty extends AbstractInjectProperty {
	
	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(MatchParamInjectProperty.class);

	/**
	 * the name
	 */
	private String name = null;
 
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param property the property that is annotated with {@link Inject}
	 */
	MatchParamInjectProperty(final ContextManagerFactory contextManagerFactory, final Method property) {
		
		super(contextManagerFactory, property);
		
		this.name = this.getMethod().getAnnotation(MatchParam.class).value();
		if (StringUtil.isBlank(this.name)) {
			this.name = Introspector.decapitalize(this.getMethod().getName().substring(3));
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractInjectProperty#get(com.corona.context.ContextManager)
	 */
	@Override
	protected Object get(final ContextManager contextManager) {

		// resolve match result from current context manager
		MatchResult result = contextManager.get(MatchResult.class);
		if (result == null) {
			this.logger.error("Request is not handled by URI pattern matching");
			throw new ConfigurationException("Request is not handled by URI pattern matching");
		}
		
		// get value from match result by parameter name
		Object value = result.get(this.name);
		if ((value == null) && (!this.isOptional())) {
			this.logger.error(
					"Matched value for [{0}] is mandatory, but resolved value is NULL", this.getType()
			);
			throw new ValueException(
					"Matched value for [{0}] is mandatory, but resolved value is NULL", this.getType()
			);
		}
		
		// if parameter value is String, will convert to expected type as field
		if ((value != null) && String.class.equals(value.getClass())) {
			return ParamUtil.getAsType((String) value, this.getType());
		}
		return value;
	}
}
