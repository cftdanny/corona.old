/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>This annotation is used to annotated a method and create HTTP response by return value of method
 * and <b>Velocity</b> template. </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Produce
public @interface Velocity {

	/**
	 * the velocity script engine 
	 */
	String engine() default "";
	
	/**
	 * the <b>Velocity</b> template to create HTTP response 
	 */
	String value();
	
	/**
	 * whether cache compiled template
	 */
	boolean cache() default false;
	
	/**
	 * the character encoding of source code
	 */
	String encoding() default "UTF-8";
}
