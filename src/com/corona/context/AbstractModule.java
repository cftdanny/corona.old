/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.annotation.Annotation;

import com.corona.context.spi.ComponentBuilder;
import com.corona.context.spi.ConstantBuilder;
import com.corona.context.spi.ExtensionBuilder;
import com.corona.context.spi.ModuleBuilder;
import com.corona.context.spi.ProviderBuilder;
import com.corona.context.spi.ScopeBuilder;
import com.corona.context.spi.ConfigurationBuilder;

/**
 * <p>A helper {@link Module} class which helps to reduce repetition and results in a more readable 
 * configuration. For a customized module, simply extends this class, implements {@link #configure()}, 
 * and call the inherited methods which mirror those found in {@link Builder}. For example: 
 * </p>
 *
 * <pre>
 * public class MyModule extends AbstractModule {
 * 	protected void configure() {
 * 		bind(PaymentService.class).as("bank").to(BankPaymentService.class);
 *	}
 * }
 * </pre>
 * 
 * <p>This class is used to configure scope, component, provider, constant and extension to context manager 
 * factory and build application runtime environment.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 * @see com.corona.context.Module
 * @see com.corona.context.Binder
 * @see com.corona.context.spi.BinderImpl
 * @see com.corona.context.spi.AnnotationBuilder
 * @see com.corona.context.spi.ComponentBuilder
 * @see com.corona.context.spi.ConstantBuilder
 * @see com.corona.context.spi.ProviderBuilder
 * @see com.corona.context.spi.ScopeBuilder
 * @see com.corona.context.spi.ContextManagerFactoryImpl
 */
public abstract class AbstractModule implements Module {

	/**
	 * the binder to bind configuration to {@link ContextManagerFactory}
	 * @see com.corona.context.spi.BinderImpl
	 */
	private Binder binder;
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Module#configure(com.corona.context.Binder)
	 */
	@Override
	// CHECKSTYLE:OFF
	public void configure(final Binder binder) {
		this.binder = binder;
		configure();
	}
	// CHECKSTYLE:ON

	/**
	 * Configures {@link ContextManagerFactory} in the exposed methods with {@link Binder} and {@link Builder}.
	*/
	protected abstract void configure();
	
	/**
	 * @return the binder to bind configuration to {@link ContextManagerFactory}
	 * @see com.corona.context.spi.BinderImpl
	 */
	protected Binder getBinder() {
		return this.binder;
	}
	
	/**
	 * <p>Bind component configuration with its injection type, name, component class and scope to 
	 * context manager factory. Bellow is example on how to bind component: </p>
	 * 
	 * <pre>
	 * 	bind(Protocol.class).as("name").to(Component.class).in(Application.class);
	 * </pre>
	 * 
	 * @param <T> the injection type
	 * @param protocolType the injection type of component
	 * @return the new component builder for injection type
	 */
	@SuppressWarnings("unchecked")
	protected <T> ComponentBuilder<T> bind(final Class<T> protocolType) {
		return (ComponentBuilder<T>) this.binder.bind(new ComponentBuilder<T>(protocolType)); 
	}
	
	/**
	 * <p>Bind constant configuration with its injection type, name, value to context manager factory in
	 * application scope. Bellow is example on how to bind constant: </p>
	 * 
	 * <pre>
	 * 	bindConstant(String.class).as("logPath").to("/var/logs/corona.log");
	 * </pre>
	 * 
	 * @param <T> the injection type
	 * @param protocolType the injection type of component
	 * @return the new constant builder
	 */
	@SuppressWarnings("unchecked")
	protected <T> ConstantBuilder<T> bindConstant(final Class<T> protocolType) {
		return (ConstantBuilder<T>) this.binder.bind(new ConstantBuilder<T>(protocolType));
	}
	
	/**
	 * <p>Bind provider configuration with its injection type, name, provider class and scope to 
	 * context manager factory. Bellow is example on how to bind provider: </p>
	 * 
	 * <pre>
	 * 	bindProvider(Protocol.class).as("name").to(Provider.class).in(Transition.class);
	 * </pre>
	 * 
	 * @param <T> the injection type
	 * @param protocolType the injection type of component
	 * @return the new provider builder for injection type
	 */
	@SuppressWarnings("unchecked")
	protected <T> ProviderBuilder<T> bindProvider(final Class<T> protocolType) {
		return (ProviderBuilder<T>) this.binder.bind(new ProviderBuilder<T>(protocolType));
	}
	
	/**
	 * <p>Bind scope configuration with its annotation type and scope component resolver to context
	 * manager factory. Bellow is example on how to bind scope:
	 * </p>
	 * 
	 * <pre>
	 * 	bindScope(Applicaton.class).to(new ApplicationScope());
	 * </pre>
	 * 
	 * @param scopeType the scope annotation type
	 * @return the new scope builder for scope annotation
	 */
	protected ScopeBuilder bindScope(final Class<? extends Annotation> scopeType) {
		return (ScopeBuilder) this.binder.bind(new ScopeBuilder(scopeType));
	}
	
	/**
	 * <p>Bind protocol configuration with protocol type, extension annotation and extension implementation class
	 * to context manager factory. Bellow is example on how to bind extension: 
	 * </p> 
	 * 
	 * <pre>
	 * 	bindExtension(MatcherFactory.class).as(SameMatch.class).to(new SameMatcherFactory());
	 * </pre>
	 * 
	 * @param <T> the protocol type 
	 * @param protocolType the protocol type of extension
	 * @return the new extension builder for protocol
	 */
	@SuppressWarnings("unchecked")
	protected <T> ExtensionBuilder<T> bindExtension(final Class<T> protocolType) {
		return (ExtensionBuilder<T>) this.binder.bind(new ExtensionBuilder<T>(protocolType));
	}
	
	/**
	 * <p>Bind a predefined module to context manager factory. For example, bind database supported components in
	 * predefined module. </p>
	 * 
	 * @param module the predefined module
	 * @return this module builder
	 */
	protected ModuleBuilder bindModule(final Module module) {
		return (ModuleBuilder) this.binder.bind(new ModuleBuilder(this.binder, module));
	}

	/**
	 * <p>This builder is used to set configuration value to component. </p>
	 * 
	 * @param <T> the protocol type
	 * @param protocolType the protocol type of component
	 * @return the setting builder
	 */
	@SuppressWarnings("unchecked")
	protected <T> ConfigurationBuilder<T> bindConfiguration(final Class<T> protocolType) {
		return (ConfigurationBuilder<T>) this.binder.bind(new ConfigurationBuilder<T>(protocolType));
	}
}
