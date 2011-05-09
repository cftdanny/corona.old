/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.extension;

import java.lang.reflect.Method;

import com.corona.context.ContextManager;

/**
 * <p>This notation is used to store a setter method that are annotated for injection values from container. </p>
 *
 * @author $Author$
 * @version $Id$
 * 
 * 
 * <p>The handler is used to set value to component by an annotated property. The value will be 
 * resolved from current context manager by property injection type. Bellow is example how to 
 * annotate property in component: 
 * </p>
 * 
 * <pre>
 * public class Component {
 * 
 * 	&#064;Inject public void setUrl(final URL url) {
 * 		// do something
 * 	}
 * }
 * </pre>
 * 
 * <p>After component is created, context manager will find all annotated properties and get value
 * from context manager and pass to component by setter of property. The value will be resolved 
 * according to configuration of property injection annotation.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.extension.DecoratedPropertyFactory
 */
public interface DecoratedProperty {

	/**
	 * @return the setter method that is annotated with annotation, for example, injection {@link Inject} annotation
	 */
	Method getMethod();

	/**
	 * <p>Set value to component. The value is resolved from current context manager by configuraiton
	 * of annotated property. 
	 * </p>
	 * 
	 * @param contextManager the current context manager
	 * @param component the component instance
	 * @throws com.corona.context.CreationException
	 */
	void set(ContextManager contextManager, Object component);
}
