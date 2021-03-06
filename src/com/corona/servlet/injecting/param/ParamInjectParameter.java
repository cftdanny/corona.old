/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.param;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.AbstractInjectParameter;
import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.Param;
import com.corona.util.ConvertUtil;
import com.corona.util.ListUtil;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ParamInjectParameter extends AbstractInjectParameter {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(ParamInjectParameter.class);

	/**
	 * the component name
	 */
	private String name = null;
	
	/**
	 * the annotated parameter
	 */
	private Param param;

	/**
	 * @param accessible the constructor or method that parameter exists in
	 * @param parameterType the class type of annotated parameter
	 * @param annotations all annotations for parameter
	 */
	ParamInjectParameter(
			final AccessibleObject accessible, final Class<?> parameterType, final Annotation[] annotations
	) {
		
		super(accessible, parameterType, annotations);
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(Param.class)) {
				this.param = (Param) annotation;
				this.name = this.param.value();
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
		
		HttpServletRequest request = contextManager.get(HttpServletRequest.class);
		if (request == null) {
			this.logger.error("Not running at Web Container, can not find Servlet Request");
			throw new ValueException("Not running at Web Container, can not find Servlet Request");
		}

		if (Collection.class.isAssignableFrom(this.getType())) {

			// Can not find generic type of parameter, only uses Collection<String>
			String[] result = request.getParameterValues(this.name);
			if ((result == null) && (!this.isOptional())) {
				this.logger.error(
						"Value of parameter [{0}] in constructor or method [{1}] resolved is mandatory, "
						+ "but resolved value is NULL", this.getType(), this.getAccessible()
				);
				throw new ValueException(
						"Value of parameter [{0}] in constructor or method [{1}] resolved is mandatory, "
						+ "but resolved value is NULL", this.getType(), this.getAccessible()
				);
			}
			return ListUtil.getAsList(result);
		} else if (this.getType().isArray()) {
			
			// Can not find generic type of parameter, only uses String[]
			String[] result = request.getParameterValues(this.name);
			if ((result == null) && (!this.isOptional())) {
				this.logger.error(
						"Value of parameter [{0}] in constructor or method [{1}] resolved is mandatory, " 
						+ "but resolved value is NULL", this.getType(), this.getAccessible()
				);
				throw new ValueException(
						"Value of parameter [{0}] in constructor or method [{1}] resolved is mandatory, " 
						+ "but resolved value is NULL", this.getType(), this.getAccessible()
				);
			}
			return result;
		} else if (ConvertUtil.canConvertFromString(this.getType())) { 
			
			// Simple type, String, Long, Integer, Short, Float, Double, Short, Byte, Boolean
			String result = request.getParameter(this.name);
			if ((result == null) && (!this.isOptional())) {
				this.logger.error(
						"Value of parameter [{0}] in constructor or method [{1}] resolved is mandatory, " 
						+ "but resolved value is NULL", this.getType(), this.getAccessible()
				);
				throw new ValueException(
						"Value of parameter [{0}] in constructor or method [{1}] resolved is mandatory, " 
						+ "but resolved value is NULL", this.getType(), this.getAccessible()
				);
			}
			return ConvertUtil.getAsType(result, this.getType());
		} else {
			
			try {
				String head = (this.param == null) ? "" : this.param.value();
				return new TokenRunner(request).getValue(this.getType(), head);
			} catch (Exception e) {
				this.logger.error("Fail to translate request parameters to class [{0}]", e, this.getType());
				throw new ValueException(
						"Fail to translate request parameters to class [{0}]", e, this.getType()
				);
			}
		}
	}
}
