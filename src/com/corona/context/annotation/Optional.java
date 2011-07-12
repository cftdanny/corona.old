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
 * <p>This annotation is used to indicate the inject value can be <code>null</code> in a field or parameter. 
 * Bellow is the sample code about how to use this annotation: </p>
 * 
 * <pre>
 * 	class TestInjectAnnotation {
 * 
 * 		&#064;Inject &#064;Optional private Component first;
 * 
 * 		&#064;Inject
 * 		public TestInjectAnnotation(&#064;Optional final Component second) {
 * 			// do something
 * 		}
 * 
 * 		&#064;Inject public void setThird(&#064;Optional final Component third) {
 * 			// do something
 * 		}
 * 	}
 * </pre>
 * 
 * <p>When context manager tries to create component, if a field or parameter is annotated with &#064;Optional, their
 * value can be <code>null</code>; otherwise, will throw {@link AsyncException}.  
 * it will resolve values from context manager and inject
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface Optional {

	/**
	 * if resolved value is null, don't bind to field or property if true 
	 */
	boolean value() default true;
}
