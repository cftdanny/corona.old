/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.reflect.Constructor;

/**
 * <p>The handler is used to create new component instance with an annotated constructor when it
 * need to be resolved by current context manager. The parameters of constructor will be resolved
 * from current context manager by parameter injection type. Bellow is example how to annotate
 * constructor for component: 
 * </p>
 * 
 * <pre>
 * public class Component {
 * 
 * 	Component(int a, int b) {
 * 		// do something
 * 	}
 * 
 * 	&#064;Inject Component(final Name("a") int a) {
 * 		// do something
 * 	}
 * }
 * </pre>
 * 
 * <p>When create component, context manager will use annotated constructor {@code Component(int a)} 
 * to create instance, the parameter {@code a} will be resolved by <i>[int.class, "a"]</i> from same
 * context manager also.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.InjectConstructorFactory
 */
public interface InjectConstructor {

	/**
	 * @return the constructor that is annotated with annotation, for example, injection {@link Inject} annotation
	 */
	Constructor<?> getConstructor();
	
	/**
	 * <p>Create component instance by constructor that is annotated with injection annotation. If there
	 * are parameters in constructor, the values will be resolved from context manager by parameters
	 * annotation. 
	 * </p>
	 * 
	 * @param contextManager the current context manager
	 * @return the new component instance
	 * @throws com.corona.context.CreationException
	 */
	Object create(ContextManager contextManager);
}
