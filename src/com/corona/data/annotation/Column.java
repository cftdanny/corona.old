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
 * <p>This annotation is used to annotated a field or a property that will map to a column of entity for specified 
 * data source.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface Column {

	/**
	 * the column name. If empty, will use field or property name in class
	 */
	String name() default "";
	
	/**
	 * whether it is temporal field. If true, will not map to column in entity 
	 */
	boolean temporal() default false; 
}
