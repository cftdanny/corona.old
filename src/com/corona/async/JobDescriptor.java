/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import java.lang.reflect.Method;

/**
 * <p>The descriptor about asynchronous method </p>
 *
 * @author $Author$
 * @version $Id$
 */
class JobDescriptor {

	/**
	 * the component type
	 */
	private Class<?> type;
	
	/**
	 * the component name
	 */
	private String name;
	
	/**
	 * the asynchronous method
	 */
	private final Method method;
	
	/**
	 * the arguments to execute component
	 */
	private final Object[] arguments;
	
	/**
	 * @param type the component type
	 * @param name the component name
	 * @param method the asynchronous method
	 * @param arguments the arguments to execute component
	 */
	public JobDescriptor(final Class<?> type, final String name, final Method method, final Object[] arguments) {
		this.type = type;
		this.name = name;
		this.method = method;
		this.arguments = arguments;
	}
	
	/**
	 * @return the component type
	 */
	public Class<?> getType() {
		return type;
	}
	
	/**
	 * @return the component name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the asynchronous method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * @return the arguments to execute component
	 */
	public Object[] getArguments() {
		return arguments;
	}
}
