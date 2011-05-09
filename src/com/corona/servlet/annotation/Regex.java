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
 * <p>This annotation is used to annotate method that will produce HTTP response according REGEX expression
 * that matches request URI. </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Match
public @interface Regex {

	/**
	 * the REGEX expression to match request URI 
	 */
	String value();
	
	/**
	 * the prefix of matched group name 
	 */
	String name() default "group";
}
