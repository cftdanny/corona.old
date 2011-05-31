/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.corona.context.annotation.Inject;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This class is used to register a method that is annotated with injection annotation. 
 * Its arguments will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AbstractInjectMethod implements InjectMethod {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(AbstractInjectMethod.class);
	
	/**
	 * the annotated property
	 */
	private Method method;

	/**
	 * the annotated parameters
	 */
	private List<InjectParameter> injectPrameters = new ArrayList<InjectParameter>();
 
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param annotatedMethod the method that is annotated with {@link Inject}
	 */
	protected AbstractInjectMethod(final ContextManagerFactory contextManagerFactory, final Method annotatedMethod) {
		
		// check the parameter of property and create its injection configuration
		this.method = annotatedMethod;
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
			InjectParameterFactory factory = contextManagerFactory.getExtension(
					InjectParameterFactory.class, annotationType
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
			
			this.injectPrameters.add(factory.create(contextManagerFactory, parameterType, annotations));
		}
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
	 * @return all inject parameters
	 */
	public List<InjectParameter> getInjectPrameters() {
		return injectPrameters;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectMethod#invoke(com.corona.context.ContextManager, java.lang.Object)
	 */
	@Override
	public Object invoke(final ContextManager contextManager, final Object component) {
		
		// get all arguments value for method
		List<Object> arguments = new ArrayList<Object>();
		for (InjectParameter parameter : this.injectPrameters) {
			arguments.add(parameter.get(contextManager));
		}
		
		// invoke method with resolved arguments
		try {
			return this.method.invoke(component, arguments.toArray());
		} catch (Throwable e) {
			this.logger.error("Fail to invoke method [{0}]", e, this.method);
			throw new CreationException("Fail to invoke method [{0}]", e, this.method);
		}
	}
}
