/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.annotation.Annotation;

import com.corona.context.annotation.Optional;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractInjectParameter implements InjectParameter {

	/**
	 * the annotated parameter
	 */
	private Class<?> type;
	
	/**
	 * whether inject value can be null
	 */
	private boolean optional = false;

	/**
	 * @param parameterType the class type of annotated parameter
	 * @param annotations all annotations for parameter
	 */
	protected AbstractInjectParameter(final Class<?> parameterType, final Annotation[] annotations) {
		
		this.type = parameterType;
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(Optional.class)) {
				this.optional = true;
				break;
			}
		}
	}
	
	/**
	 * @return whether inject value can be null
	 */
	public boolean isOptional() {
		return optional;
	}
	
	/**
	 * @param optional whether inject value can be null to set
	 */
	public void setOptional(final boolean optional) {
		this.optional = optional;
	}

	/**
	 * @return the annotated parameter type
	 */
	public Class<?> getType() {
		return type;
	}
}
