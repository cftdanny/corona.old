/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.corona.context.AbstractInjectProperty;
import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to register a setter method that is annotated with injection annotation. 
 * Its value will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class AsyncInjectProperty extends AbstractInjectProperty {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(AsyncInjectProperty.class);
	
	/**
	 * the name
	 */
	private String name = null;
 
	/**
	 * the scheduler name
	 */
	private String schedulerName;

	/**
	 * @param contextManagerFactory the context manager factory
	 * @param property the property that is annotated with {@link Inject}
	 */
	AsyncInjectProperty(final ContextManagerFactory contextManagerFactory, final Method property) {
		super(contextManagerFactory, property);
		
		// find component and scheduler to execute asynchronous method
		Async async = property.getAnnotation(Async.class);
		
		this.name = async.value();
		if (StringUtil.isBlank(this.name)) {
			this.name = null;
		}
		
		this.schedulerName = async.scheduler();
		if (StringUtil.isBlank(this.schedulerName)) {
			this.schedulerName = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractInjectProperty#get(com.corona.context.ContextManager)
	 */
	@Override
	protected Object get(final ContextManager contextManager) {

		// find component to be injected, if null, throw error
		Object component =  contextManager.get(this.getType(), this.name);
		if (component == null) {
			this.logger.error("Component [{0}/{1}] does not exist, should register it", this.getType(), this.name);
			throw new ValueException(
					"Component [{0}/{1}] does not exist, should register first", this.getType(), this.name
			);
		}
		
		// find scheduler that is used to schedule asynchronous method
		Scheduler scheduler = contextManager.get(Scheduler.class, this.schedulerName);
		if (scheduler == null) {
			this.logger.error(
					"Scheduler [{0}/{1}] does not exist, should register it", Scheduler.class, this.schedulerName
			);
			throw new ValueException(
					"Scheduler [{0}/{1}] does not exist, should register first", Scheduler.class, this.schedulerName
			);
		}

		// create proxy instance with injected component, and dispatch asynchronous method to schedule
		return Proxy.newProxyInstance(
				this.getClass().getClassLoader(), new Class<?>[] {this.getType()}, new AsyncProxyComponent(
						scheduler, component, this.getType(), this.name
				)
		);
	}
}
