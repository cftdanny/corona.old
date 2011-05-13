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
 * <p>This annotation is used to define an entity structure information in entity class </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {

	/**
	 * the entity schema. For database, it is schema name
	 */
	String schema() default "";
	
	/**
	 * the entity family. For database, it is table name
	 * @return
	 */
	String name() default "";
	
	/**
	 * the primary keys
	 */
	PrimaryKey primaryKey();
	
	/**
	 * all unique keys
	 */
	UniqueKey[] uniqueKeys() default { };
	
	/**
	 * all indexes
	 */
	Index[] indexes() default { };
}
