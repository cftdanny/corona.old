/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.corona.context.annotation.InjectType;

/**
 * <p>This annotation is used to annotate method that enable remote call </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@InjectType
@Produce
public @interface Remote {

	/**
	 * marshal data object by Apache Avro
	 */
	String AVRO = "avro";
	
	/**
	 * marshal data object by Jackson JSON
	 */
	String JSON = "json";
	
	/**
	 * marshal data object by JAXB XML
	 */
	String XML = "xml";
	
	/**
	 * how to marshal data object 
	 */
	String value() default AVRO;
}
