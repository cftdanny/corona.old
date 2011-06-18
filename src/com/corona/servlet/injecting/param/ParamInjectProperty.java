/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.param;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.AbstractInjectProperty;
import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.Param;
import com.corona.util.ConvertUtil;
import com.corona.util.ListUtil;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to register a setter method that is annotated with injection annotation. 
 * Its value will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ParamInjectProperty extends AbstractInjectProperty {
	
	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(ParamInjectField.class);

	/**
	 * the name
	 */
	private String name = null;
 
	/**
	 * the annotated parameter
	 */
	private Param param;
	
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param property the property that is annotated with {@link Inject}
	 */
	ParamInjectProperty(final ContextManagerFactory contextManagerFactory, final Method property) {
		
		super(contextManagerFactory, property);
		
		this.param = this.getMethod().getAnnotation(Param.class);
		this.name = this.param.value();
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
			return ListUtil.getAsList(result);
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
		} else if (ConvertUtil.canConvertFromString(this.getType())) {
			
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
