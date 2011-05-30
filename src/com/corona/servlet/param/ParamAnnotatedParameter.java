/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.AbstractAnnotatedParameter;
import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.Param;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ParamAnnotatedParameter extends AbstractAnnotatedParameter {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(ParamAnnotatedParameter.class);

	/**
	 * the component name
	 */
	private String name = null;
	
	/**
	 * @param parameterType the class type of annotated parameter
	 * @param annotations all annotations for parameter
	 */
	ParamAnnotatedParameter(final Class<?> parameterType, final Annotation[] annotations) {
		
		super(parameterType, annotations);
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(Param.class)) {
				this.name = ((Param) annotation).value();
				break;
			}
		}
		
		if (StringUtil.isBlank(this.name)) {
			this.logger.error("@Param annotaton at parameter [{0}] does not define its name", parameterType);
			throw new ConfigurationException(
					"@Param annotaton at parameter [{0}] does not define its name", parameterType
			);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AnnotatedParameter#get(com.corona.context.ContextManager)
	 */
	@Override
	public Object get(final ContextManager contextManager) {
		
		HttpServletRequest request = contextManager.get(HttpServletRequest.class);
		if (request == null) {
			this.logger.error("Not running at Web Container, can not find Servlet Request");
			throw new ValueException("Not running at Web Container, can not find Servlet Request");
		}

		if (Collection.class.isAssignableFrom(this.getType())) {

			// Can not find generic type of parameter, only uses Collection<String>
			List<String> result = new ArrayList<String>();
			String[] values = request.getParameterValues(this.name);
			if (values != null) {
				for (String value : values) {
					result.add(value);
				}
			}
			return result;
		} else if (this.getType().isArray()) {
			
			// Can not find generic type of parameter, only uses String[]
			String[] result = request.getParameterValues(this.name);
			if ((result == null) && (!this.isOptional())) {
				this.logger.error(
						"Value of parameter [{0}] resolved is mandatory, but resolved value is NULL", this.getType()
				);
				throw new ValueException(
						"Value of parameter [{0}] resolved is mandatory, but resolved value is NULL", this.getType()
				);
			}
			return result;
		} else { 
			
			// Simple type, String, Long, Integer, Short, Float, Double, Short, Byte, Boolean
			String result = request.getParameter(this.name);
			if ((result == null) && (!this.isOptional())) {
				this.logger.error(
						"Value of parameter [{0}] resolved is mandatory, but resolved value is NULL", this.getType()
				);
				throw new ValueException(
						"Value of parameter [{0}] is mandatory, but resolved value is NULL", this.getType()
				);
			}
			return result == null ? null : StringUtil.to(result, this.getType());
		}
	}
}
