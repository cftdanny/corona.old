/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>This annotation is used to define command in a class. </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NamedCommand {

	/**
	 * the name of command
	 */
	String name() default "";
	
	/**
	 * the default script of command statement. If data source is database, it is SQL 
	 */
	String value();
	
	/**
	 * the special script for specified data source
	 */
	Statement[] statements() default { };
}
