/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.reflect.Field;


/**
 * <p>This factory is used to create {@link InjectField} for an annotated field of in a component. An
 * {@link InjectFieldFactory} is associated with an injection annotation in {@link ContextManagerFactory}, 
 * for example, {@link com.corona.context.spi.DefaultInjectFieldFactory} is associated with 
 * {@link com.corona.context.annotation.Inject}, and creates {@link com.corona.context.spi.DefaultInjectField} 
 * for a field that is annotated by {@link com.corona.context.annotation.Inject}.
 * </p>
 * 
 * <p>If implements field injection, it needs: </p>
 * <ol>
 * 	<li>Create an annotation, this annotation must annotated with {@link com.corona.context.annotation.InjectType}. 
 * 	</li>
 * 	<li>Create subclass {@link InjectField}, it will set value of field that is resolved from context manager. 
 * 	</li>
 * 	<li>Create subclass {@link InjectFieldFactory}, it will create {@link InjectField} according to 
 * context manager factory and annotated field.
 * 	</li>
 * 	<li>Register new injection annotation and {@link InjectFieldFactory} to context manager factory
 * 	in {@link Module} or {@link AbstractModule}. 
 * 	</li>
 * </ol>
 * 
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.AbstractModule
 * @see com.corona.context.InjectField
 * @see com.corona.context.spi.ContextManagerFactoryImpl
 */
public interface InjectFieldFactory {

	/**
	 * <p>Create configuration for a field of component that is annotated with injection annotation. The
	 * creation factory to will be resolved by injection annotation in context manager factory.
	 * </p>
	 * 
	 * @param contextManagerFactory the context manager factory
	 * @param field the field of component that is annotated with injection annotation
	 * @return the annotated field
	 * @throws com.corona.context.ConfigurationException
	 */
	InjectField create(ContextManagerFactory contextManagerFactory, Field field);
}
