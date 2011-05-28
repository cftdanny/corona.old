/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.annotation.Annotation;

import com.corona.context.Builder;
import com.corona.context.ContextManagerFactory;
import com.corona.context.ContextUtil;
import com.corona.context.Key;
import com.corona.context.Provider;
import com.corona.context.annotation.Context;
import com.corona.context.annotation.Install;
import com.corona.context.annotation.Name;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.util.StringUtil;

/**
 * <p>This builder is used to build component class as component descriptor with {@link Provider} and 
 * then bind it to context manager factory. 
 * </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the injection type of component
 */
public class ProviderBuilder<T> implements Builder<T> {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(ComponentBuilder.class);

	/**
	 * the component alias
	 */
	private String alias;

	/**
	 * the injection type of component
	 */
	private Class<T> type;
	
	/**
	 * the component name
	 */
	private String name = null;
	
	/**
	 * the implementation class of component
	 */
	private Class<? extends Provider<T>> clazz = null;
	
	/**
	 * the scope type
	 */
	private Class<? extends Annotation> scope = Context.class;
	
	/**
	 * <p>Build provider class by its type and name, and then bind it to context manager factory. </p>
	 * 
	 * @param protocolType the injection type of component
	 */
	public ProviderBuilder(final Class<T> protocolType) {
		this.type = protocolType;
	}

	/**
	 * <p>This method is used to give a specified name to a provider component. </p>
	 * 
	 * @param componentName the component name
	 * @return this builder
	 */
	public ProviderBuilder<T> as(final String componentName) {
		this.name = componentName;
		return this;
	}

	/**
	 * <p>Set implementation provider class to component builder. </p>
	 * 
	 * @param implementationClass the implementation class of component
	 * @return this builder
	 */
	public ProviderBuilder<T> to(final Class<? extends Provider<T>> implementationClass) {
		
		// check whether class is annotated with name. if yes, get component name
		this.clazz = implementationClass;
		if (this.clazz.isAnnotationPresent(Name.class)) {
			this.name = this.clazz.getAnnotation(Name.class).value();
		}
		
		// try to find component scope annotation and set its scope if it present
		Annotation annotation = ContextUtil.findScopeAnnotation(this.clazz);
		if (annotation != null) {
			this.scope = annotation.annotationType();
		}
		
		return this;
	}

	/**
	 * <p>Bind provider component to a scope by scope type. </p>
	 * 
	 * @param scopeType the scope type
	 * @return this builder
	 */
	public ProviderBuilder<T> in(final Class<? extends Annotation> scopeType) {
		this.scope = scopeType;
		return this;
	}

	/**
	 * @param componentAlias the component alias
	 * @return this builder
	 */
	public ProviderBuilder<T> alias(final String componentAlias) {
		this.alias = componentAlias;
		return this;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Builder#build(com.corona.context.ContextManagerFactory)
	 */
	@Override
	public void build(final ContextManagerFactory contextManagerFactory) {
		
		// check whether this component can be installed or not
		if (this.clazz.isAnnotationPresent(Install.class)) {
			for (String dependency : this.clazz.getAnnotation(Install.class).dependencies()) {
				if (!this.isClassLoadable(dependency)) {
					return;
				}
			}
		}

		// create provider descriptor and register to context manager factory
		ProviderDescriptor<T> descriptor = new ProviderDescriptor<T>(contextManagerFactory, this.clazz, this.scope);
		if (!StringUtil.isBlank(this.alias)) {
			descriptor.setAlias(this.alias);
		}
		((ContextManagerFactoryImpl) contextManagerFactory).getDescriptors().put(
				new Key<T>(this.type, this.name), descriptor
		);
	}

	/**
	 * @param className the class name
	 * @return whether class can be loaded or not
	 */
	private boolean isClassLoadable(final String className) {

		boolean loadable = false;
		try {
			this.getClass().getClassLoader().loadClass(className);
			loadable = true;
			
		} catch (Throwable e) {
			this.logger.error("Class [{0}] can not be loaded, will not install this component", className);
		}
		return loadable;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Provider Builder, ");
		sb.append("class (" + this.clazz + "), ");
		sb.append("scope (" + this.scope + ")");
		return sb.toString();
	}
}
