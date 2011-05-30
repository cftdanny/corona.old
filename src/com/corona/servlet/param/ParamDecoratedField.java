/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.AbstractDecoratedField;
import com.corona.context.ContextManager;
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
public class ParamDecoratedField extends AbstractDecoratedField {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(ParamDecoratedField.class);
	
	/**
	 * the component name
	 */
	private String name;
	
	/**
	 * @param field the field that is annotated with an annotation type
	 */
	ParamDecoratedField(final Field field) {
		
		// construct super class and get parameter name
		super(field);
		this.name = field.getAnnotation(Param.class).value();
		if (StringUtil.isBlank(this.name)) {
			this.name = field.getName();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractDecoratedField#get(com.corona.context.ContextManager)
	 */
	@Override
	protected Object get(final ContextManager contextManager) {
		
		HttpServletRequest request = contextManager.get(HttpServletRequest.class);
		if (request == null) {
			this.logger.error("Not run at Servlet environment, can not find Servlet request");
			
		}
		return request.getParameter(this.name);
	}
}
