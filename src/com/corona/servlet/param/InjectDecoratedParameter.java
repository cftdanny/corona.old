/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.lang.annotation.Annotation;

import com.corona.context.ContextManager;
import com.corona.context.CreationException;
import com.corona.context.annotation.Name;
import com.corona.context.annotation.Optional;
import com.corona.context.extension.DecoratedParameter;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class InjectDecoratedParameter implements DecoratedParameter {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(InjectDecoratedParameter.class);

	/**
	 * the annotated parameter
	 */
	private Class<?> protocolType;
	
	/**
	 * the component name
	 */
	private String name = null;
	
	/**
	 * whether inject value can be null
	 */
	private boolean optional = false;

	/**
	 * @param parameterType the class type of annotated parameter
	 * @param annotations all annotations for parameter
	 */
	InjectDecoratedParameter(final Class<?> parameterType, final Annotation[] annotations) {
		
		this.protocolType = parameterType;
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(Name.class)) {
				this.name = ((Name) annotation).value();
			}
			if (annotation.annotationType().equals(Optional.class)) {
				this.optional = true;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.extension.DecoratedParameter#get(com.corona.context.ContextManager)
	 */
	@Override
	public Object get(final ContextManager contextManager) {
		
		Object value = contextManager.get(this.protocolType, this.name);
		
		// try to test whether value of field can be null (optional) or not
		if ((value == null) && (!this.optional)) {
			this.logger.error("The value of parameter [{0}] is mandatory, can not be NULL", this.protocolType);
			throw new CreationException(
					"The value of parameter [{0}] is mandatory, can not be NULL", this.protocolType
			);
		}
		
		return value;
	}
}
