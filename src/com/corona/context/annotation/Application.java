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
 * <p>This annotation is used to indicate component to be an application scope component if component
 * is annotated with it or component is bound to it. The application scope component can be defined by 
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
 * 			this.bind(Example1.class).to(Example1.class).in(Application.class);
 * 		}
 * 	}
 * 
 * 	// Example 2:
 * 	&#64;Application
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
 * <p>Application scope component is managed by context manager factory, all context managers created
 * by same context manager factory will share same instance of application scope component.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ScopeType
public @interface Application {

}
