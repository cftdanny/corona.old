/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.reflect.Method;

/**
 * <p>This handler is used to invoke a method of component. The argument of method will be resolved
 * from current context manager. The method should be annotated with an injection annotation. Bellow is 
 * example how to inject value to method:
 * </p>
 * 
 * <pre>
 * 	public class Component {
 * 	
 * 		&#64;Inject public execute(final String first);
 * 	}
 * </pre>
 * 
 * @author $Author$
 * @version $Id$
 */
public interface InjectMethod {

	/**
	 * @return the method that is annotated with annotation, for example, injection {@link Inject} annotation
	 */
	Method getMethod();
	
	/**
	 * <p>Invoke a method with current context manager and component. The values of arguments are also resolved
	 * from current context manager.
	 * </p>
	 * 
	 * @param contextManager current context manager
	 * @param component the component
	 * @return the outcome that method returns
	 */
	Object invoke(ContextManager contextManager, Object component);
}
