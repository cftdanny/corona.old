/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.extension;

import java.lang.reflect.Field;

import com.corona.context.ContextManagerFactory;

/**
 * <p>This factory is used to create {@link DecoratedField} for an annotated field of in a component. An
 * {@link DecoratedFieldFactory} is associated with an injection annotation in {@link ContextManagerFactory}, 
 * for example, {@link com.corona.context.spi.InjectDecoratedFieldFactory} is associated with 
 * {@link com.corona.context.annotation.Inject}, and creates {@link com.corona.context.spi.InjectDecoratedField} 
 * for a field that is annotated by {@link com.corona.context.annotation.Inject}.
 * </p>
 * 
 * <p>If implements field injection, it needs: </p>
 * <ol>
 * 	<li>Create an annotation, this annotation must annotated with {@link com.corona.context.annotation.InjectType}. 
 * 	</li>
 * 	<li>Create subclass {@link DecoratedField}, it will set value of field that is resolved from context manager. 
 * 	</li>
 * 	<li>Create subclass {@link DecoratedFieldFactory}, it will create {@link DecoratedField} according to 
 * context manager factory and annotated field.
 * 	</li>
 * 	<li>Register new injection annotation and {@link DecoratedFieldFactory} to context manager factory
 * 	in {@link Module} or {@link AbstractModule}. 
 * 	</li>
 * </ol>
 * 
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.AbstractModule
 * @see com.corona.context.extension.DecoratedField
 * @see com.corona.context.spi.ContextManagerFactoryImpl
 */
public interface DecoratedFieldFactory {

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
	DecoratedField create(ContextManagerFactory contextManagerFactory, Field field);
}
