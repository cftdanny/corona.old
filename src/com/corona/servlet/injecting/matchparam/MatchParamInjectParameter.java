/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.matchparam;

import java.lang.annotation.Annotation;

import com.corona.context.AbstractInjectParameter;
import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.MatchResult;
import com.corona.servlet.annotation.MatchParam;
import com.corona.servlet.injecting.param.ParamUtil;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class MatchParamInjectParameter extends AbstractInjectParameter {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(MatchParamInjectParameter.class);

	/**
	 * the component name
	 */
	private String name = null;

	/**
	 * @param parameterType the class type of annotated parameter
	 * @param annotations all annotations for parameter
	 */
	MatchParamInjectParameter(final Class<?> parameterType, final Annotation[] annotations) {
		
		super(parameterType, annotations);
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(MatchParam.class)) {
				this.name = ((MatchParam) annotation).value();
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
