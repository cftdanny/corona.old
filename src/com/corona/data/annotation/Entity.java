/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.annotation;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public @interface Entity {

	/**
	 * the entity schema. For database, it is schema name
	 */
	String schema() default "";
	
	/**
	 * the entity family. For database, it is table name
	 * @return
	 */
	String value() default "";
}
