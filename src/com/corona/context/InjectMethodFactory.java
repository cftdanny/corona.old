/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.reflect.Method;


/**
 * <p>This factory is used to create {@link InjectMethod} for an annotated method (setter) of in a 
 * component. 
 * </p>
 *
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.AbstractModule
 * @see com.corona.context.InjectMethod
 * @see com.corona.context.spi.ContextManagerFactoryImpl
 */
public interface InjectMethodFactory {

	/**
	 * <p>Create configuration for a property in component that is annotated with injection annotation. The
	 * creation factory to will be resolved by injection annotation in current context manager factory.
	 * </p>
	 * 
	 * @param contextManagerFactory current the context manager factory
	 * @param method the method annotated with injection annotation
	 * @return the annotated method
	 * @throws com.corona.context.ConfigurationException
	 */
	InjectMethod create(ContextManagerFactory contextManagerFactory, Method method);
}
