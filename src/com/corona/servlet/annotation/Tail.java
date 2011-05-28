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
 * <p>Annotated to a method in order to match URI with tail string </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Match
public @interface Tail {

	/**
	 * the match priority
	 */
	int priority() default 1000;

	/**
	 * The head pattern of URI
	 */
	String value();
	
	/**
	 * the name of tail after strips prefix from URI
	 * @return
	 */
	String name() default "prefix";
}
