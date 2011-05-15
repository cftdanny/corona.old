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
	 * <p>This enumerator is used </p>
	 */
	public enum MappingBy {
		
		/**
		 * Do not map implicitly, use {@link Column} to map column explicitly
		 */
		NONE,
		
		/**
		 * Map column by field in class implicitly
		 */
		FIELD,
		
		/**
		 * Map column by property in class implicitly
		 */
		PROPERTY
	}
	
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
	 * how to map field or property to column default 
	 */
	MappingBy mappingBy() default MappingBy.FIELD;
	
	/**
	 * the primary key 
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
