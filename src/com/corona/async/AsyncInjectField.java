/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import com.corona.context.AbstractInjectField;
import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class AsyncInjectField extends AbstractInjectField {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(AsyncInjectField.class);
	
	/**
	 * the component name
	 */
	private String name;
	
	/**
	 * the scheduler name
	 */
	private String schedulerName;
	
	/**
	 * @param field the field that is annotated with an annotation type
	 */
	AsyncInjectField(final Field field) {
		
		// construct super class and get parameter name
		super(field);
		
		// find component and scheduler to execute asynchronous method
		Async async = field.getAnnotation(Async.class);
		
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
	 * @see com.corona.context.AbstractInjectField#get(com.corona.context.ContextManager)
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
