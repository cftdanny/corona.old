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
 * <p>This annotation is used to inject value from context manager by the configuration of annotated
 * field, constructor or property. Bellow is the sample code about how to use this annotation: 
 * </p>
 * 
 * <pre>
 * 	class TestInjectAnnotation {
 * 
 * 		&#064;Inject private Component first;
 * 
 * 		&#064;Inject
 * 		public TestInjectAnnotation(final Component second) {
 * 			// do something
 * 		}
 * 
 * 		&#064;Inject public void setThird(final Component third) {
 * 			// do something
 * 		}
 * 	}
 * </pre>
 * 
 * <p>When context manager creates component, it will resolve values from context manager and inject
 * them into component. 
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
	ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD
})
@InjectType
public @interface Inject {

	/**
	 * whether injection value is optional or not. If true, value can be <code>null</code>
	 */
	boolean value() default false;
}
