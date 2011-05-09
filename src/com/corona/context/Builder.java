/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

/**
 * <p>The {@link Builder} is used to configure context manager factory by information collected in {@link Module}.
 * For example: 
 * </p>
 * 
 * <ul>
 * 	<li>Component builder ({@link com.corona.context.spi.ComponentBuilder} is used to register component class
 * 	as a new component into context manager factory.
 * 	</li>
 * 	<li>Constant builder ({@link com.corona.context.spi.ConstantBuilder} is used to register a constant value 
 * 	as component into context manager factory.
 * 	</li>
 * 	<li>Provider builder ({@link com.corona.context.spi.ProviderBuilder} is used to register a provider class 
 * 	as component into context manager factory.
 * 	</li>
 * </ul>
 *
 * <p>All builders is configured in {@code configure} method of {@link com.context.Module}. After all modules are 
 * loaded, context manager factory will configure its runtime environment by these builders.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the injection type
 * @see com.corona.context.Binder
 * @see com.corona.context.ContextManager
 * @see com.corona.context.spi.BinderImpl
 * @see com.corona.context.spi.ContextManagerImpl
 */
public interface Builder<T> {

	/**
	 * <p>Configure current context manager factory with information in builder. For example, register component,
	 * scope, etc into context manager factory.  
	 * </p>
	 * 
	 * @param contextManagerFactory the current context manager factory
	 */
	void build(ContextManagerFactory contextManagerFactory);
}
