/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.annotation.Annotation;

/**
 * <p>The descriptor is used to store component configuration in context manager factory. Descriptor is created
 * in context manager factory when load {@link Module}, and used to resolve component value in context manager.
 * </p>
 * 
 * @author $Author$
 * @version $Id$
 * @param <T> the injection type
 */
public interface Descriptor<T> {
	
	/**
	 * @return the component alias
	 */
	String getAlias();
	
	/**
	 * @return the component version
	 */
	int getVersion();
	
	/**
	 * @return the scope type of component that is defined by annotation
	 */
	Class<? extends Annotation> getScopeType();
	
	/**
	 * @return the implementation class of component
	 */
	Class<?> getImplementationClass();
	
	/**
	 * @return the component class
	 */
	Class<?> getComponentClass();
	
	/**
	 * @param contextManager the current context manager
	 * @return the component value resolved from current context manager
	 */
	T getValue(ContextManager contextManager);
	
	/**
	 * @param configuration the configuration to set value to component property
	 */
	void configure(Configuration configuration);
}
