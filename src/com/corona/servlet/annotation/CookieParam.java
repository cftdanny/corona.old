/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.corona.context.annotation.InjectType;

/**
 * <p>This annotation is used to inject value of cookie to field, property or parameter of
 * component </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER }) 
@InjectType
public @interface CookieParam {

	/**
	 * the matched parameter name 
	 */
	String value() default "";
}
