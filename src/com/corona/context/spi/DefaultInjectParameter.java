/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

import com.corona.context.AbstractInjectParameter;
import com.corona.context.ContextManager;
import com.corona.context.CreationException;
import com.corona.context.annotation.Name;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class DefaultInjectParameter extends AbstractInjectParameter {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(DefaultInjectParameter.class);
	
	/**
	 * the component name
	 */
	private String name = null;
	
	/**
	 * @param accessible the constructor or method that parameter exists in
	 * @param parameterType the class type of annotated parameter
	 * @param annotations all annotations for parameter
	 */
	DefaultInjectParameter(
			final AccessibleObject accessible, final Class<?> parameterType, final Annotation[] annotations
	) {

		super(accessible, parameterType, annotations);
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(Name.class)) {
				this.name = ((Name) annotation).value();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectParameter#get(com.corona.context.ContextManager)
	 */
	@Override
	public Object get(final ContextManager contextManager) {
		
		// try to test whether value of field can be null (optional) or not
		Object value = contextManager.get(this.getType(), this.name);
		if ((value == null) && (!this.isOptional())) {
			this.logger.error(
					"The value of parameter [{0}] in method or constructor [{1}] is mandatory, can not be NULL", 
					this.getType(), this.getAccessible()
			);
			throw new CreationException(
					"The value of parameter [{0}] in method or constructor [{1}] is mandatory, can not be NULL", 
					this.getType(), this.getAccessible()
			);
		}
		return value;
	}
}
