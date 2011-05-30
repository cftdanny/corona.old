/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.reflect.Method;

import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.CreationException;
import com.corona.context.InjectProperty;
import com.corona.context.ValueException;
import com.corona.context.annotation.Inject;
import com.corona.context.annotation.Optional;
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
class DefaultInjectProperty implements InjectProperty {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(DefaultInjectProperty.class);
	
	/**
	 * the annotated property
	 */
	private Method property;

	/**
	 * the protocol type
	 */
	private Class<?> protocolType;
	
	/**
	 * the name
	 */
	private String name = null;

	/**
	 * whether value to be injected can be null
	 */
	private boolean optional = false;
 
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param property the property that is annotated with {@link Inject}
	 */
	DefaultInjectProperty(final ContextManagerFactory contextManagerFactory, final Method property) {
		
		// store this property in order to set component property later
		this.property = property;
		
		if (this.protocolType.isAnnotationPresent(Optional.class)) {
			this.optional = true;
		}
		this.protocolType = this.property.getParameterTypes()[0];
		this.name = this.property.getAnnotation(Inject.class).value();
		if (StringUtil.isBlank(this.name)) {
			this.name = null;
		}
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
	 * {@inheritDoc}
	 * @see com.corona.context.InjectProperty#set(com.corona.context.ContextManager, java.lang.Object)
	 */
	@Override
	public void set(final ContextManager contextManager, final Object component) {
		
		Object value = contextManager.get(this.protocolType, this.name);
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
}
