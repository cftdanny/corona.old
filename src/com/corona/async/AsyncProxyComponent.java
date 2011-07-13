/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>This dispatcher is used to schedule asynchronous method to be executed by scheduling </p>
 *
 * @author $Author$
 * @version $Id$
 */
class AsyncProxyComponent implements InvocationHandler {

	/**
	 * the scheduler that is used to execute asynchronous method
	 */
	private Scheduler scheduler;
	
	/**
	 * the component that is in proxy state
	 */
	private Object component;
	
	/**
	 * the component type
	 */
	private Class<?> type;
	
	/**
	 * the component name
	 */
	private String name;
	
	/**
	 * @param scheduler the scheduler that is used to execute asynchronous method
	 * @param component the component that is in a proxy state
	 * @param type the component type
	 * @param name the component name
	 */
	AsyncProxyComponent(final Scheduler scheduler, final Object component, final Class<?> type, final String name) {
		this.scheduler = scheduler;
		this.component = component;
		this.type = type;
		this.name = name;
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		// find method in component with interface method
		Method jobMethod = this.component.getClass().getMethod(method.getName(), method.getParameterTypes());
		
		// if method is not annotated with Async, don't need schedule it as asynchronous method
		Job job = jobMethod.getAnnotation(Job.class);
		if (job == null) {
			return jobMethod.invoke(this.component, args);
		}
		
		// if annotated with Async, schedule it as asynchronous method, and return null
		JobDescriptor descriptor = new JobDescriptor(this.type, this.name, jobMethod, args);
		this.scheduler.schedule(descriptor);
		
		return null;
	}
}
