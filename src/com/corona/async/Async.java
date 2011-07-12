/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.corona.context.annotation.InjectType;

/**
 * <p>This annotation is used to annotation method or field to run in asynchronous </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
@InjectType
public @interface Async {

	/**
	 * the component name to be injected 
	 */
	String value() default "";
	
	/**
	 * the schedule is used to execute asynchronous method
	 */
	String scheduler() default "";
}
