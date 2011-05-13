/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.annotation;

/**
 * <p>This class is used to define named queries in a class. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public @interface NamedQuery {

	/**
	 * the name of query 
	 */
	String name() default "";
	
	/**
	 * the default script of query statement. If data source is database, it is SQL 
	 */
	String value();
	
	/**
	 * the special script for specified data source
	 */
	Statement[] statements() default { };
}
