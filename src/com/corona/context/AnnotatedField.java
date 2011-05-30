/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.reflect.Field;


/**
 * <p>This handler is used to set value to field of component by resolving value from current 
 * context manager. The field should be annotated with an injection annotation. Bellow is example
 * how to inject value to field:
 * </p>
 * 
 * <pre>
 * 	public class Component {
 * 	
 * 		&#64;Inject private Another another;
 * 	}
 * </pre>
 * 
 * <p>After component is created, the value of field {@code another} will be set by resolving 
 * [Another.class] in current context manager.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.AnnotatedFieldFactory
 */
public interface AnnotatedField {

	/**
	 * @return the field that is annotated with annotation, for example, injection {@link Inject} annotation
	 */
	Field getField();
	
	/**
	 * <p>Set value to a field of component. The value is resolved from current context manager according its
	 * injection configuration. </p>
	 * 
	 * @param contextManager the current context manager
	 * @param component the component
	 * @throws com.corona.context.CreationException
	 */
	void set(ContextManager contextManager, Object component);
}
