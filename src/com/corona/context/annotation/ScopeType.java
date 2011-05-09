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
 * <p>This annotation is used to annotate an annotation as scope annotation. For example, bellow example
 * is how to annotate {@code Application} scope: </p>
 * 
 * <pre>
 * 	&#64;ScopeType
 * 	public @interface Application {
 * 	}
 * </pre>
 * 
 * <p>The above example defines an <b>Application</b> scope annotation, and it can be used to define
 * application scope component.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ScopeType {

}
