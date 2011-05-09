/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.ContextUtil;
import com.corona.context.CreationException;
import com.corona.context.annotation.Inject;
import com.corona.context.extension.DecoratedParameter;
import com.corona.context.extension.DecoratedParameterFactory;
import com.corona.context.extension.DecoratedMethod;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This class is used to register a method that is annotated with injection annotation. 
 * Its arguments will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class InjectDecoratedMethod implements DecoratedMethod {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(InjectDecoratedMethod.class);
	
	/**
	 * the annotated property
	 */
	private Method method;

	/**
	 * the annotated parameters
	 */
	private List<DecoratedParameter> prameters = new ArrayList<DecoratedParameter>();
 
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param annotatedMethod the method that is annotated with {@link Inject}
	 */
	InjectDecoratedMethod(final ContextManagerFactoryImpl contextManagerFactory, final Method annotatedMethod) {
		
		// store this property in order to set component property later
		this.method = annotatedMethod;
		
		// check the parameter of property and create its injection configuration
		for (int i = 0, count = this.method.getParameterTypes().length; i < count; i++) {
			
			Class<?> parameterType = this.method.getParameterTypes()[i];
			Annotation[] annotations = this.method.getParameterAnnotations()[i];
			
			// find annotation in setter method that can be injected
			Class<? extends Annotation> annotationType = Inject.class;
			Annotation annotation = ContextUtil.findInjectAnnotation(annotations);
			if (annotation != null) {
				annotationType = annotation.annotationType();
			}
			
			// create annotated parameter factory by annotated parameter in property
			DecoratedParameterFactory factory = contextManagerFactory.getExtension(
					DecoratedParameterFactory.class, annotationType
			);
			if (factory == null) {
				this.logger.error(
						"Annotated method factory [{0}] for parameter [{1}] in method [{2}] does not exists",
						annotationType, i, annotatedMethod
				);
				throw new ConfigurationException(
						"Annotated method factory [{0}] for parameter [{1}] in method [{2}] does not exists",
						annotationType, i, annotatedMethod
				);
			}
			
			this.prameters.add(factory.create(contextManagerFactory, parameterType, annotations));
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.extension.DecoratedMethod#getMethod()
	 */
	@Override
	public Method getMethod() {
		return this.method;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.extension.DecoratedMethod#invoke(com.corona.context.ContextManager, java.lang.Object)
	 */
	@Override
	public Object invoke(final ContextManager contextManager, final Object component) {
		
		// get all arguments value for method
		List<Object> arguments = new ArrayList<Object>();
		for (DecoratedParameter parameter : this.prameters) {
			arguments.add(parameter.get(contextManager));
		}
		
		// invoke method with resolved arguments
		try {
			return this.method.invoke(component, prameters.toArray());
		} catch (Throwable e) {
			this.logger.error("Fail to invoke method [{0}]", e, this.method);
			throw new CreationException("Fail to invoke method [{0}]", e, this.method);
		}
	}
}
