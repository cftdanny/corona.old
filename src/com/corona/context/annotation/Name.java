/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>This annotation is used to assign name to component, or resolve named component from
 * context manager in field or parameter. </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER })
public @interface Name {

	/**
	 * the component name
	 */
	String value();
}
