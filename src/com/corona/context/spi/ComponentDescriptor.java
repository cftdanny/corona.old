/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.ContextUtil;
import com.corona.context.CreationException;
import com.corona.context.Descriptor;
import com.corona.context.annotation.Create;
import com.corona.context.annotation.Inject;
import com.corona.context.annotation.Version;
import com.corona.context.extension.DecoratedConstructor;
import com.corona.context.extension.DecoratedConstructorFactory;
import com.corona.context.extension.DecoratedFieldFactory;
import com.corona.context.extension.DecoratedMethod;
import com.corona.context.extension.DecoratedMethodFactory;
import com.corona.context.extension.DecoratedProperty;
import com.corona.context.extension.DecoratedPropertyFactory;
import com.corona.context.extension.DecoratedField;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>Component descriptor is created by {@link ComponentBuilder} and register to context manager factory.
 * Context manager factory will use this descriptor to create instance if required.
 * </p> 
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the injection type
 * @see ComponentBuilder
 * @see ContextManagerFactoryImpl
 */
public class ComponentDescriptor<T> implements Descriptor<T> {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(ComponentDescriptor.class);
	
	/**
	 * the component version
	 */
	private int version = 1;
	
	/**
	 * the implementation class of component
	 */
	private Class<? extends T> clazz;
	
	/**
	 * the scope type annotation
	 */
	private Class<? extends Annotation> scope;
	
	/**
	 * the constructor with injection annotation to create component instance
	 */
	private DecoratedConstructor annotatedConstructor = null;
	
	/**
	 * all annotated fields for inject value just component is created
	 */
	private List<DecoratedField> annotatedFields = new ArrayList<DecoratedField>();
	
	/**
	 * all annotated properties for inject value just component is created
	 */
	private List<DecoratedProperty> annotatedProperties = new ArrayList<DecoratedProperty>();
	
	/**
	 * the method that will be invoked just after component instance is created
	 */
	private DecoratedMethod createMethod = null;
	
	/**
	 * @param contextManagerFactory the current context manager to build this component descriptor
	 * @param componentClass the implementation class of component
	 * @param scope the scope type annotation
	 */
	ComponentDescriptor(final ContextManagerFactory contextManagerFactory, 
			final Class<? extends T> componentClass, final Class<? extends Annotation> scope
	) {
		this.clazz = componentClass;
		this.scope = scope;

		// try to get component version by component class
		if (this.clazz.isAnnotationPresent(Version.class)) {
			this.version = this.clazz.getAnnotation(Version.class).value();
		}
		
		// configure component descriptor for all annotations to be injected
		this.configure((ContextManagerFactoryImpl) contextManagerFactory);
	}
	
	/**
	 * @param contextManagerFactory the context manager factory
	 */
	private void configure(final ContextManagerFactory contextManagerFactory) {
		
		// Find a constructor that is annotated with injection annotation
		for (Constructor<?> constructor : this.clazz.getConstructors()) {
			
			Annotation annotation = ContextUtil.findInjectAnnotation(constructor);
			if (annotation != null) {
				DecoratedConstructorFactory factory = contextManagerFactory.getExtension(
						DecoratedConstructorFactory.class, annotation.annotationType()
				); 
				if (factory == null) {
					this.logger.error("Annotated constructor factory for [{0}] does not exists", constructor);
					throw new ConfigurationException(
							"Annotated constructor factory for [{0}] does not exists", constructor
					);
				}
				this.annotatedConstructor = factory.create(contextManagerFactory, constructor);
				
				break;
			}
		}
		
		// check all fields to verify whether they need to inject value
		for (Field field : this.clazz.getDeclaredFields()) {
			
			Annotation annotation = ContextUtil.findInjectAnnotation(field);
			if (annotation != null) {
				DecoratedFieldFactory factory = contextManagerFactory.getExtension(
						DecoratedFieldFactory.class, annotation.annotationType()
				); 
				if (factory == null) {
					this.logger.error("Field annotation factory for [{0}] does not exists", field);
					throw new ConfigurationException(
							"Field annotation factory for [{0}] does not exists", field
					);
				}
				
				this.annotatedFields.add(factory.create(contextManagerFactory, field));
			}
		}
		
		// check all properties to verify whether they need to inject value
		for (Method method : this.clazz.getDeclaredMethods()) {
			
			// check whether method is property method
			if (ContextUtil.isSetterMethod(method)) {
				Annotation annotation = ContextUtil.findInjectAnnotation(method);
				if (annotation != null) {
					DecoratedPropertyFactory factory = contextManagerFactory.getExtension(
							DecoratedPropertyFactory.class, annotation.annotationType()
					); 
					if (factory == null) {
						this.logger.error("Property annotation factory for [{0}] does not exists", annotation);
						throw new ConfigurationException(
							"Property annotation factory for [{0}] does not exists", annotation
						);
					}
				
					this.annotatedProperties.add(factory.create(contextManagerFactory, method));
				}
			}
			
			// check whether method is create method
			if (method.isAnnotationPresent(Create.class)) {
				
				Class<? extends Annotation> annotationType = Inject.class;
				Annotation annotation = ContextUtil.findInjectAnnotation(method);
				if (annotation != null) {
					annotationType = annotation.annotationType();
				}
				
				DecoratedMethodFactory factory = contextManagerFactory.getExtension(
						DecoratedMethodFactory.class, annotationType
				); 
				if (factory == null) {
					this.logger.error("Property method factory for [{0}] does not exists", annotationType);
					throw new ConfigurationException(
						"Property annotation method for [{0}] does not exists", annotationType
					);
				}
				
				this.createMethod = factory.create(contextManagerFactory, method);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#getVersion()
	 */
	@Override
	public int getVersion() {
		return this.version;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#getScopeType()
	 */
	@Override
	public Class<? extends Annotation> getScopeType() {
		return this.scope;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#getImplementationClass()
	 */
	@Override
	public Class<?> getImplementationClass() {
		return this.clazz;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#getComponentClass()
	 */
	@Override
	public Class<?> getComponentClass() {
		return this.clazz;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#getValue(com.corona.context.ContextManager)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getValue(final ContextManager contextManager) {

		T component = null;
		if (this.annotatedConstructor != null) {
			component = (T) this.annotatedConstructor.create(contextManager);
		} else {
			try {
				component = this.clazz.newInstance();
			} catch (Throwable e) {
				this.logger.error("Fail to create component instance by class [{0}]", e, this.clazz);
				throw new CreationException(
						"Fail to create component instance by class [{0}]", e, this.clazz
				);
			}
		}
		
		// inject value from context manager to all annotated fields
		for (DecoratedField annotatedField : this.annotatedFields) {
			annotatedField.set(contextManager, component);
		}
		for (DecoratedProperty annotatedProperty : this.annotatedProperties) {
			annotatedProperty.set(contextManager, component);
		}
		
		// invoke method that is annotated with Create annotation
		if (this.createMethod != null) {
			this.createMethod.invoke(contextManager, component);
		}
		
		return component;
	}
}
