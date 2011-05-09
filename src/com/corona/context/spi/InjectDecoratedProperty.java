/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.ContextUtil;
import com.corona.context.CreationException;
import com.corona.context.annotation.Inject;
import com.corona.context.extension.DecoratedParameter;
import com.corona.context.extension.DecoratedParameterFactory;
import com.corona.context.extension.DecoratedProperty;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This class is used to register a setter method that is annotated with injection annotation. 
 * Its value will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class InjectDecoratedProperty implements DecoratedProperty {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(InjectDecoratedProperty.class);
	
	/**
	 * the annotated property
	 */
	private Method property;

	/**
	 * the annotated parameters
	 */
	private DecoratedParameter prameter;
 
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param property the property that is annotated with {@link Inject}
	 */
	InjectDecoratedProperty(final ContextManagerFactory contextManagerFactory, final Method property) {
		
		// store this property in order to set component property later
		this.property = property;
		
		// check the parameter of property and create its injection configuration
		Class<?> parameterType = this.property.getParameterTypes()[0];
		
		// find annotation in setter method that can be injected
		Class<? extends Annotation> annotationType = Inject.class;
		Annotation annotation = ContextUtil.findInjectAnnotation(this.property.getParameterAnnotations()[0]);
		if (annotation != null) {
			annotationType = annotation.annotationType();
		}
		
		// create annotated parameter factory by annotated parameter in property
		DecoratedParameterFactory factory = contextManagerFactory.getExtension(
				DecoratedParameterFactory.class, annotationType
		);
		if (factory == null) {
			this.logger.error("Annotated parameter factory [{0}] for [{1}] does not exists",
					annotationType, property
			);
			throw new ConfigurationException("Annotated parameter factory [{0}] for [{1}] does not exists",
					annotationType, property
			);
		}
		
		this.prameter = factory.create(
				contextManagerFactory, parameterType, this.property.getParameterAnnotations()[0]
		);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.extension.DecoratedProperty#getMethod()
	 */
	@Override
	public Method getMethod() {
		return this.property;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.extension.DecoratedProperty#set(com.corona.context.ContextManager, java.lang.Object)
	 */
	@Override
	public void set(final ContextManager contextManager, final Object component) {
		
		try {
			this.property.invoke(component, this.prameter.get(contextManager));
		} catch (Throwable e) {
			this.logger.error("Fail set value to property [{0}]", this.property);
			throw new CreationException("Fail set value to property [{0}]", this.property);
		}
	}
}
