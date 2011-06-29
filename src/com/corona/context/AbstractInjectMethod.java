/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.annotation.Inject;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.ContentType;
import com.corona.servlet.annotation.Expiration;
import com.corona.util.ContextUtil;

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
	 * how to control HTTP response expires
	 */
	private Expiration expiration;
	
	/**
	 * the HTTP content type that is annotated in method of component
	 */
	private ContentType contentType;

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

		// get expiration or content type if it is annotated
		this.expiration = this.method.getAnnotation(Expiration.class);
		this.contentType = this.method.getAnnotation(ContentType.class);
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
		
		// set HTTP expires by Expires annotation in producer method of component
		HttpServletResponse response = contextManager.get(HttpServletResponse.class);
		if ((this.expiration == null) || (this.expiration.value() < 0)) {
			
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setDateHeader("Last-Modified", System.currentTimeMillis());
		} else {
			
			Long current = System.currentTimeMillis();
			response.setHeader("Cache-Control", "max-age=" + (this.expiration.value() / 1000));
			response.setDateHeader("Expires", current + this.expiration.value());
			response.setDateHeader("Last-Modified", current);
		}

		// set HTTP response content type if configured by annotation in producer method
		if (this.contentType != null) {
			response.setContentType(this.contentType.value());
		}

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
