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
 * and <b>FreeMaker</b> template. </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Produce
public @interface FreeMaker {

	/**
	 * the name of FreeMaker engine
	 */
	String engine() default "";
	
	/**
	 * the name of FreeMaker template 
	 */
	String value();
	
	/**
	 * whether enable theme for produced content 
	 */
	boolean theme() default true;
}
