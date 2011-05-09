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
 * <p>This annotation is used to indicate component to be a transition scope component if component
 * is annotated with it or component is bound to it. The transition scope component can be defined by 
 * bellow 2 examples:
 * </p>
 * 
 * <pre>
 * 	// Example 1:
 * 	public class Example1 {
 * 	}
 * 
 * 	public Example1Module extends AbstractModule {
 * 		protected configure() {
 * 			this.bind(Example1.class).to(Example1.class).in(Transition.class);
 * 		}
 * 	}
 * 
 * 	// Example 2:
 * 	&#64;Transition
 * 	public class Example1 {
 * 	}
 * 
 * 	public Example1Module extends AbstractModule {
 * 		protected configure() {
 * 			this.bind(Example1.class).to(Example1.class);
 * 		}
 * 	}
 * </pre>
 * 
 * <p>In same context manager, every time when transition scope component is required, it will create 
 * an instance of transition scope component.
 * </p> 
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ScopeType
public @interface Transition {

}
