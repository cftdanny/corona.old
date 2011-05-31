/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.reflect.Method;

import com.corona.context.annotation.Optional;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This class is used to register a setter method that is annotated with injection annotation. 
 * Its value will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractInjectProperty implements InjectProperty {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(AbstractInjectProperty.class);
	
	/**
	 * the annotated property
	 */
	private Method property;

	/**
	 * the protocol type
	 */
	private Class<?> protocolType;
	
	/**
	 * whether value to be injected can be null
	 */
	private boolean optional = false;
 
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param property the property that is annotated with {@link Inject}
	 */
	protected AbstractInjectProperty(final ContextManagerFactory contextManagerFactory, final Method property) {
		
		// store this property in order to set component property later
		this.property = property;
		if (this.protocolType.isAnnotationPresent(Optional.class)) {
			this.optional = true;
		}
		this.protocolType = this.property.getParameterTypes()[0];
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectProperty#getMethod()
	 */
	@Override
	public Method getMethod() {
		return this.property;
	}
	
	/**
	 * @return whether value to be injected can be null
	 */
	public boolean isOptional() {
		return optional;
	}
	
	/**
	 * @param optional whether value to be injected can be null to set
	 */
	public void setOptional(final boolean optional) {
		this.optional = optional;
	}
	
	/**
	 * @return the protocol type
	 */
	public Class<?> getType() {
		return this.protocolType;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectProperty#set(com.corona.context.ContextManager, java.lang.Object)
	 */
	@Override
	public void set(final ContextManager contextManager, final Object component) {
		
		Object value = this.get(contextManager);
		if ((value == null) && (!this.optional)) {
			this.logger.error("Resolved value is null, but property [{0}] is mandatory.", this.property);
			throw new ValueException("Resolved value is null, but property [{0}] is mandatory.", this.property);
		}

		try {
			this.property.invoke(component, value);
		} catch (Throwable e) {
			this.logger.error("Fail set value to property [{0}]", e, this.property);
			throw new CreationException("Fail set value to property [{0}]", e, this.property);
		}
	}
	
	/**
	 * @param contextManager the current context manager
	 * @return value resolved from current context
	 */
	protected abstract Object get(final ContextManager contextManager);
}
