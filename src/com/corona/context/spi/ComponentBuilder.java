/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.annotation.Annotation;

import com.corona.context.Builder;
import com.corona.context.ContextManagerFactory;
import com.corona.context.ContextUtil;
import com.corona.context.Key;
import com.corona.context.annotation.Context;
import com.corona.context.annotation.Dependency;
import com.corona.context.annotation.Name;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>Component builder is used to build component class to component descriptor and register it to context 
 * manager factory.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the injection type of component
 */
public class ComponentBuilder<T> implements Builder<T> {
	
	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(ComponentBuilder.class);
	
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
	private Class<? extends T> clazz = null;
	
	/**
	 * the scope type, default is Context scope
	 */
	private Class<? extends Annotation> scope = Context.class;
	
	/**
	 * <p>Create component builder by an injection type. Usually, the injection type is interface of 
	 * component, but it can also be an implementation class of component. 
	 * </p>
	 * 
	 * @param protocolType the injection type of component
	 */
	public ComponentBuilder(final Class<T> protocolType) {
		this.type = protocolType;
	}

	/**
	 * <p>Bind name to component. </p>
	 * 
	 * @param componentName the component name
	 * @return this builder
	 */
	public ComponentBuilder<T> as(final String componentName) {
		this.name = componentName;
		return this;
	}

	/**
	 * <p>Set implementation class. </p>
	 * 
	 * @param implementationClass the implementation class of component
	 * @return this builder
	 */
	public ComponentBuilder<T> to(final Class<? extends T> implementationClass) {
		
		// check whether class is annotated with name. if yes, get component name
		this.clazz = implementationClass;
		if (this.clazz.isAnnotationPresent(Name.class)) {
			this.name = this.clazz.getAnnotation(Name.class).value();
		}
		
		// try to find component scope annotation and set its scope if it present
		Annotation annotation = ContextUtil.findScopeAnnotation(this.clazz);
		if (annotation != null) {
			this.scope = annotation.getClass();
		}
		
		return this;
	}

	/**
	 * <p>Bind component to scope. </p>
	 * 
	 * @param scopeType the scope type
	 * @return this builder
	 */
	public ComponentBuilder<T> in(final Class<? extends Annotation> scopeType) {
		this.scope = scopeType;
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Builder#build(com.corona.context.ContextManagerFactory)
	 */
	@Override
	public void build(final ContextManagerFactory contextManagerFactory) {
		
		// check whether this component can be installed or not
		if (this.clazz.isAnnotationPresent(Dependency.class)) {
			for (String dependency : this.clazz.getAnnotation(Dependency.class).value()) {
				if (!this.isClassLoadable(dependency)) {
					return;
				}
			}
		}

		// install component by its key and descriptor to context manager factory
		((ContextManagerFactoryImpl) contextManagerFactory).getDescriptors().put(
				new Key<T>(this.type, this.name), 
				new ComponentDescriptor<T>(contextManagerFactory, this.clazz, this.scope)
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
		sb.append("Component Builder, ");
		sb.append("class (" + this.clazz + "), ");
		sb.append("scope (" + this.scope + ")");
		return sb.toString();
	}
}
