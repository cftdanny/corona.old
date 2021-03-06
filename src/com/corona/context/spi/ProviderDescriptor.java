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

import com.corona.context.InjectConstructor;
import com.corona.context.InjectConstructorFactory;
import com.corona.context.InjectField;
import com.corona.context.InjectFieldFactory;
import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.CreationException;
import com.corona.context.Descriptor;
import com.corona.context.InjectMethod;
import com.corona.context.InjectMethodFactory;
import com.corona.context.InjectProperty;
import com.corona.context.InjectPropertyFactory;
import com.corona.context.Provider;
import com.corona.context.Configuration;
import com.corona.context.annotation.Alias;
import com.corona.context.annotation.Create;
import com.corona.context.annotation.Inject;
import com.corona.context.annotation.Version;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.util.ContextUtil;

/**
 * <p>The component descriptor is used to bind a class as a component to context manager factory. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the injection type
 */
public class ProviderDescriptor<T> implements Descriptor<T> {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(ProviderDescriptor.class);
	
	/**
	 * the component alias
	 */
	private String alias;
	
	/**
	 * the component version
	 */
	private int version = 1;
	
	/**
	 * the implementation class of component
	 */
	private Class<? extends Provider<T>> implementationClass;
	
	/**
	 * the component class
	 */
	private Class<T> componentClass = null;
	
	/**
	 * the scope type annotation
	 */
	private Class<? extends Annotation> scope;
	
	/**
	 * the constructor with injection annotation to create component instance
	 */
	private InjectConstructor annotatedConstructor = null;
	
	/**
	 * all annotated fields for inject value just component is created
	 */
	private List<InjectField> annotatedFields = new ArrayList<InjectField>();
	
	/**
	 * all annotated properties for inject value just component is created
	 */
	private List<InjectProperty> annotatedProperties = new ArrayList<InjectProperty>();
	
	/**
	 * the method that will be invoked just after component instance is created
	 */
	private InjectMethod createMethod = null;

	/**
	 * the setting
	 */
	private List<ConfigurationDescriptor> configurationDescriptors = null;

	/**
	 * @param contextManagerFactory the current context manager to build this component descriptor
	 * @param componentClass the implementation class of component
	 * @param scope the scope type annotation
	 */
	ProviderDescriptor(final ContextManagerFactory contextManagerFactory, 
			final Class<? extends Provider<T>> componentClass, final Class<? extends Annotation> scope
	) {
		this.implementationClass = componentClass;
		this.scope = scope;
		
		// try to find component alias by Alias annotation
		if (this.implementationClass.isAnnotationPresent(Alias.class)) {
			this.alias = this.implementationClass.getAnnotation(Alias.class).value();
		}

		// try to get component version by component class
		if (this.implementationClass.isAnnotationPresent(Version.class)) {
			this.version = this.implementationClass.getAnnotation(Version.class).value();
		}

		// configure component descriptor for all annotations to be injected
		this.configure(contextManagerFactory);
	}
	
	/**
	 * @param contextManagerFactory the context manager factory
	 */
	@SuppressWarnings("unchecked")
	private void configure(final ContextManagerFactory contextManagerFactory) {
		
		// Find a constructor that is annotated with injection annotation
		for (Constructor<?> constructor : this.implementationClass.getConstructors()) {
			
			Annotation annotation = ContextUtil.findInjectAnnotation(constructor);
			if (annotation != null) {
				InjectConstructorFactory factory = contextManagerFactory.getExtension(
						InjectConstructorFactory.class, annotation.annotationType()
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
		for (Field field : this.implementationClass.getDeclaredFields()) {
			
			Annotation annotation = ContextUtil.findInjectAnnotation(field);
			if (annotation != null) {
				InjectFieldFactory factory = contextManagerFactory.getExtension(
						InjectFieldFactory.class, annotation.annotationType()
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
		for (Method method : this.implementationClass.getDeclaredMethods()) {
			
			// check whether method is property method
			if (ContextUtil.isSetterMethod(method)) {
				Annotation annotation = ContextUtil.findInjectAnnotation(method);
				if (annotation != null) {
					InjectPropertyFactory factory = contextManagerFactory.getExtension(
							InjectPropertyFactory.class, annotation.annotationType()
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
				
				InjectMethodFactory factory = contextManagerFactory.getExtension(
						InjectMethodFactory.class, annotationType
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

		// find component class by get method and context manager parameter in provider
		try {
			this.componentClass = (Class<T>) this.implementationClass.getMethod("get").getReturnType();
		} catch (Throwable e) {
			this.componentClass = null;
		}
	}
	
	/**
	 * @param alias the component alias
	 */
	void setAlias(final String alias) {
		this.alias = alias;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#getAlias()
	 */
	@Override
	public String getAlias() {
		return this.alias;
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
		return this.implementationClass;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#getComponentClass()
	 */
	@Override
	public Class<?> getComponentClass() {
		return this.componentClass;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#getValue(com.corona.context.ContextManager)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getValue(final ContextManager contextManager) {

		Provider<T> provider = null;
		if (this.annotatedConstructor != null) {
			provider = (Provider<T>) this.annotatedConstructor.create(contextManager);
		} else {
			try {
				provider = this.implementationClass.newInstance();
			} catch (Throwable e) {
				this.logger.error("Fail to create component instance by class [{0}]", e, this.implementationClass);
				throw new CreationException(
						"Fail to create component instance by class [{0}]", e, this.implementationClass
				);
			}
		}
		
		// set setting values to all setting properties
		if (this.configurationDescriptors != null) {
			for (ConfigurationDescriptor descriptor : this.configurationDescriptors) {
				descriptor.setValue(contextManager, provider);
			}
		}

		// inject value from context manager to all annotated fields
		for (InjectField annotatedField : this.annotatedFields) {
			annotatedField.set(contextManager, provider);
		}
		for (InjectProperty annotatedProperty : this.annotatedProperties) {
			annotatedProperty.set(contextManager, provider);
		}
		
		// invoke method that is annotated with Create annotation
		if (this.createMethod != null) {
			this.createMethod.invoke(contextManager, provider);
		}

		return provider.get();
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#configure(com.corona.context.Configuration)
	 */
	@Override
	public void configure(final Configuration configuration) {
		
		if (this.configurationDescriptors == null) {
			this.configurationDescriptors = new ArrayList<ConfigurationDescriptor>();
		}
		this.configurationDescriptors.add(new ConfigurationDescriptor(this.implementationClass, configuration));
	}
}
