/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.corona.context.annotation.Inject;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This class is used to create new component instance by constructor annotated with {@link Inject}. The
 * parameters value of constructor will be resolved from current context manager. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractInjectConstructor implements InjectConstructor {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(AbstractInjectConstructor.class);
	
	/**
	 * the constructor
	 */
	private Constructor<?> constructor;
	
	/**
	 * the annotated parameters in constructor
	 */
	private List<InjectParameter> parameters = new ArrayList<InjectParameter>();
	
	/**
	 * @param contextManagerFactory the current context manager factory
	 * @param constructor the constructor
	 */
	protected AbstractInjectConstructor(
			final ContextManagerFactory contextManagerFactory, final Constructor<?> constructor) {
		
		// store constructor in order to create component instance later
		this.constructor = constructor;
		
		// create all parameters handler from constructor, uses to create component instance
		for (int i = 0, count = this.constructor.getParameterTypes().length; i < count; i++) {
			
			Class<?> parameterType = this.constructor.getParameterTypes()[i];
			Annotation[] annotations = this.constructor.getParameterAnnotations()[i];
			
			Class<? extends Annotation> annotationType = Inject.class;
			Annotation annotation = ContextUtil.findInjectAnnotation(annotations);
			if (annotation != null) {
				annotationType = annotation.annotationType();
			}
			
			InjectParameterFactory factory = contextManagerFactory.getExtension(
					InjectParameterFactory.class, annotationType
			);
			if (factory == null) {
				this.logger.error(
						"Parameter factory [{0}] for parameter [{1}] in constructor [{2}] does not exists",
						annotationType, parameterType, this.constructor
				);
				throw new ConfigurationException(
						"Parameter factory [{0}] for parameter [{1}] in constructor [{2}] does not exists",
						annotationType, parameterType, this.constructor
				);
			}

			this.parameters.add(factory.create(contextManagerFactory, parameterType, annotations));
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectConstructor#getConstructor()
	 */
	@Override
	public Constructor<?> getConstructor() {
		return this.constructor;
	}
	
	/**
	 * @return the annotated parameters
	 */
	protected List<InjectParameter> getAnnotatedParameters() {
		return parameters;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectConstructor#create(com.corona.context.ContextManager)
	 */
	@Override
	public Object create(final ContextManager contextManager) {
		
		// resolve values by all annotated parameters
		List<Object> values = new ArrayList<Object>();
		for (InjectParameter parameter : this.parameters) {
			values.add(parameter.get(contextManager));
		}
		
		this.logger.debug("Try to create component instance by constructor [{0}]", this.constructor);
		try {
			return this.constructor.newInstance(values.toArray());
		} catch (Throwable e) {
			this.logger.error("Fail to create component instance by constructor [{0}]", e, this.constructor);
			throw new CreationException(
					"Fail to create component instance by constructor [{0}]", e, this.constructor
			);
		}
	}
}
