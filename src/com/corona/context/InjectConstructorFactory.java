/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.reflect.Constructor;

/**
 * <p>This factory is used to create {@link InjectConstructor} for an annotated constructor of in a component.
 * An {@link InjectConstructorFactory} is associated with an injection annotation in 
 * {@link ContextManagerFactory}, for example, {@link com.corona.context.spi.DefaultInjectConstructorFactory}
 * is associated with {@link com.corona.context.annotation.Inject}, and creates 
 * {@link com.corona.context.spi.DefaultInjectConstructor} for a constructor that is annotated by 
 * {@link com.corona.context.annotation.Inject}.
 * </p>
 * 
 * <p>If implements constructor injection, it needs: </p>
 * <ol>
 * 	<li>Create an annotation, this annotation must annotated with {@link com.corona.context.annotation.InjectType}. 
 * 	</li>
 * 	<li>Create subclass {@link InjectConstructor}, it will create component instance by annotated constructor. 
 * 	</li>
 * 	<li>Create subclass {@link InjectConstructorFactory}, it will create {@link InjectConstructor} 
 * 	according to context manager factory and annotated constructor.
 * 	</li>
 * 	<li>Register new injection annotation and {@link InjectConstructorFactory} to context manager factory
 * 	in {@link Module} or {@link AbstractModule}. 
 * 	</li>
 * </ol>
 *
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.AbstractModule
 * @see com.corona.context.InjectConstructor
 * @see com.corona.context.spi.ContextManagerFactoryImpl
 */
public interface InjectConstructorFactory {

	/**
	 * <p>Create configuration for a constructor of component that is annotated with injection annotation. The
	 * creation factory to will be resolved by injection annotation in context manager factory.
	 * </p>
	 * 
	 * @param contextManagerFactory the context manager factory
	 * @param constructor the constructor of component that is annotated with injection annotation
	 * @return the annotated constructor
	 * @throws com.corona.context.ConfigurationException
	 */
	InjectConstructor create(ContextManagerFactory contextManagerFactory, Constructor<?> constructor);
}
