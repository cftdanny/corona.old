/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

import java.lang.reflect.Method;

import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.InjectMethod;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.Remote;

/**
 * <p>This class is used to register a method that is annotated with injection annotation. 
 * Its arguments will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class RemoteInjectMethod implements InjectMethod {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(RemoteInjectMethod.class);
	
	/**
	 * the annotated property
	 */
	private Method method;

	/**
	 * the marshaller and unmarshaller name
	 */
	private String name;
	
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param annotatedMethod the method that is annotated with {@link Remote}
	 */
	RemoteInjectMethod(final ContextManagerFactory contextManagerFactory, final Method annotatedMethod) {
		this.method = annotatedMethod;
		this.name = this.method.getAnnotation(Remote.class).value();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectMethod#getMethod()
	 */
	@Override
	public Method getMethod() {
		return this.method;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectMethod#invoke(com.corona.context.ContextManager, java.lang.Object)
	 */
	@Override
	public Object invoke(final ContextManager contextManager, final Object component) {
		return null;
	}
	
	
}
