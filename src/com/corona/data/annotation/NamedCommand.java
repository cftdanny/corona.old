/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.annotation;

/**
 * <p>This annotation is used to define command in a class. </p>
 *
 * @author $Author$
 * @version $Id$
 */
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
