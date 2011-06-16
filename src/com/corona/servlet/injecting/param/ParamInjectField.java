/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.param;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.AbstractInjectField;
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
class ParamInjectField extends AbstractInjectField {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(ParamInjectField.class);
	
	/**
	 * the component name
	 */
	private String name;
	
	/**
	 * @param field the field that is annotated with an annotation type
	 */
	ParamInjectField(final Field field) {
		
		// construct super class and get parameter name
		super(field);
		this.name = field.getAnnotation(Param.class).value();
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
		
		HttpServletRequest request = contextManager.get(HttpServletRequest.class);
		if (request == null) {
			this.logger.error("Not running at Web Container, can not find Servlet Request");
			throw new ValueException("Not running at Web Container, can not find Servlet Request");
		}
		
		if (Collection.class.isAssignableFrom(this.getType())) {
			
			String[] result = request.getParameterValues(this.name);
			if ((result == null) && (!this.isOptional())) {
				this.logger.error(
						"Value of parameter [{0}] resolved is mandatory, but resolved value is NULL", this.getType()
				);
				throw new ValueException(
						"Value of parameter [{0}] resolved is mandatory, but resolved value is NULL", this.getType()
				);
			}

			// the inject to is Collection/List, will convert parameter to list
			Type[] types = ((ParameterizedType) this.getField().getGenericType()).getActualTypeArguments();
			Type type = ((types == null) || (types.length == 0)) ? String.class : types[0];

			return ParamUtil.getAsList(result, type);
		} else if (this.getType().isArray()) {

			// the inject to type is array, but can not find generic type of parameter, only uses String[]
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
		} else if (ParamUtil.isSimpleType(this.getType())) {
			
			// it is simple type, will transfer by supported simple type
			String result = request.getParameter(this.name);
			if ((result == null) && (!this.isOptional())) {
				this.logger.error(
						"Value of parameter [{0}] resolved is mandatory, but resolved value is NULL", this.getType()
				);
				throw new ValueException(
						"Value of parameter [{0}] is mandatory, but resolved value is NULL", this.getType()
				);
			}
			return ParamUtil.getAsType(result, this.getType());
		} else {
			
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
}
