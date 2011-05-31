/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Object get(final ContextManager contextManager) {
		
		HttpServletRequest request = contextManager.get(HttpServletRequest.class);
		if (request == null) {
			this.logger.error("Not running at Web Container, can not find Servlet Request");
			throw new ValueException("Not running at Web Container, can not find Servlet Request");
		}
		
		if (Collection.class.isAssignableFrom(this.getType())) {
			
			Type[] types = ((ParameterizedType) this.getField().getGenericType()).getActualTypeArguments();
			Type type = ((types == null) || (types.length == 0)) ? String.class : types[0];

			List result = new ArrayList();
			String[] values = request.getParameterValues(this.name);
			if (values != null) {
				for (String value : values) {
					result.add(StringUtil.to(value, type));
				}
			}
			return result;
		} else if (this.getType().isArray()) {

			// Can not find generic type of parameter, only uses String[]
			return request.getParameterValues(this.name);
		} else {
			
			return StringUtil.to(request.getParameter(this.name), this.getType());
		}
	}
}
