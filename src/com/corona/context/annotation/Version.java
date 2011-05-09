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
 * <p>This component is used to define the version of component. When install component
 * to context manager factory, new version (larger value) will override old version. For
 * example, if there are two components with same key, but different version 1 and 2, 
 * context manager will install version 2. </p>
 * 
 * <p>If a component is not annotated with {@link Version}, context manager factory will
 * assume it uses default version: 1.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Version {

	/**
	 * the component version 
	 */
	int value();
}
