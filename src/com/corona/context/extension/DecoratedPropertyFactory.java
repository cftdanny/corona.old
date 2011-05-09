/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.extension;

import java.lang.reflect.Method;

import com.corona.context.ContextManagerFactory;

/**
 * <p>This factory is used to create {@link DecoratedProperty} for an annotated property (setter) of in a 
 * component. An {@link DecoratedPropertyFactory} is associated with an injection annotation in 
 * {@link ContextManagerFactory}, for example, {@link com.corona.context.spi.InjectDecoratedPropertyFactory}
 * is associated with {@link com.corona.context.annotation.Inject}, and creates 
 * {@link com.corona.context.spi.InjectDecoratedProperty} for a method that is annotated by 
 * {@link com.corona.context.annotation.Inject}.
 * </p>
 * 
 * <p>If implements property injection, it needs: </p>
 * <ol>
 * 	<li>Create an annotation, this annotation must annotated with {@link com.corona.context.annotation.InjectType}. 
 * 	</li>
 * 	<li>Create subclass {@link DecoratedProperty}, it will create component instance by annotated property. 
 * 	</li>
 * 	<li>Create subclass {@link DecoratedPropertyFactory}, it will create {@link DecoratedProperty} 
 * 	according to context manager factory and annotated property.
 * 	</li>
 * 	<li>Register new injection annotation and {@link DecoratedPropertyFactory} to context manager factory
 * 	in {@link Module} or {@link AbstractModule}. 
 * 	</li>
 * </ol>
 *
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.AbstractModule
 * @see com.corona.context.extension.DecoratedProperty
 * @see com.corona.context.spi.ContextManagerFactoryImpl
 */
public interface DecoratedPropertyFactory {

	/**
	 * <p>Create configuration for a property in component that is annotated with injection annotation. The
	 * creation factory to will be resolved by injection annotation in current context manager factory.
	 * </p>
	 * 
	 * @param contextManagerFactory current the context manager factory
	 * @param property the property annotated with injection annotation
	 * @return the annotated property
	 * @throws com.corona.context.ConfigurationException
	 */
	DecoratedProperty create(ContextManagerFactory contextManagerFactory, Method property);
}
