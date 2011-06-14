/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.AbstractInjectParameter;
import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ParamsInjectParameter extends AbstractInjectParameter {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(ParamsInjectParameter.class);
	
	/**
	 * @param parameterType the class type of annotated parameter
	 * @param annotations all annotations for parameter
	 */
	ParamsInjectParameter(final Class<?> parameterType, final Annotation[] annotations) {
		super(parameterType, annotations);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectParameter#get(com.corona.context.ContextManager)
	 */
	@Override
	public Object get(final ContextManager contextManager) {
		
		HttpServletRequest request = contextManager.get(HttpServletRequest.class);
		if (request == null) {
			this.logger.error("Not running at Web Container, can not find Servlet Request");
			throw new ValueException("Not running at Web Container, can not find Servlet Request");
		}

		try {
			return new TokenRunner(request).getValue(this.getType());
		} catch (Exception e) {
			this.logger.error("Fail to translate request parameters to class [{0}]", e, this.getType());
			throw new ValueException(
					"Fail to translate request parameters to class [{0}]", e, this.getType()
			);
		}
	}
}
