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
 * <p>This annotation is used to annotate an annotation as inject annotation. For example, bellow example
 * is how to annotate {@code Inject} scope: </p>
 * 
 * <pre>
 * 	&#64;InjectType
 * 	public @interface Inject {
 * 	}
 * </pre>
 * 
 * <p>The above example defines an <b>Inject</b> annotation, it can be used to define how to inject value
 * to component.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface InjectType {

}
