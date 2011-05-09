/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.extension;

import com.corona.context.ContextManager;

/**
 * <p>This handler is used to get injection value for parameter of constructor or method when invoke them. 
 * The value is resolved from context manager by parameter type or other annotations. Bellow is example how 
 * to inject value for parameter to constructor or method.
 * </p>
 * 
 * <pre>
 * 	public class Component {
 * 		
 * 		&#64;Inject public Component(@Name("path") final String path, @Jndi("database") final Database database) {
 * 			// do something
 * 		} 
 * 	}
 * </pre>
 * 
 * <p>In above example, when create component, the value parameter path and database will be resolved from current
 * context manager, and these value will be used to create component by constructor.  
 * </p>
 *
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.extension.DecoratedParameterFactory
 */
public interface DecoratedParameter {

	/**
	 * <p>Get value from current context manager by injection annotation configuration of parameter. The value will be
	 * used when invoke constructor, property or method.
	 * </p>
	 * 
	 * @param contextManager the current context manager
	 * @return the value retrieve from context manager according to parameter annotation
	 * @throws com.corona.context.CreationException
	 */
	Object get(ContextManager contextManager);
}
