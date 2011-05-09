/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

/**
 * <p>{@link Binder} is used to bind configuration that is defined builder to context manager factory,
 * for example, {@link Binder} can bind component from component builder to context manager factory.
 * </p>
 * 
 * <p>The subclass {@link com.corona.context.spi.BinderImpl} will collect all {@link Builder} that are
 * bound in {@link Module}. After all {@link Module} are loaded, context manager factory will uses builders
 * in binder to configure its runtime environment.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.Build
 * @see com.corona.context.spi.BinderImpl
 * @see com.corona.context.ContextManager
 * @see com.corona.context.spi.ContextManagerImpl
 */
public interface Binder {
	
	/**
	 * <p>Bind a builder to binder in order to configure context manager factory after all {@link Module} loaded. 
	 * The argument builder will return and it will be used to configure other parameter for builder in
	 * {@link Module}. 
	 * </p>
	 * 
	 * @param builder the builder
	 * @return the builder
	 */
	Builder<?> bind(final Builder<?> builder);
}
