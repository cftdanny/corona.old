/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import com.corona.context.Builder;
import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.Descriptor;
import com.corona.context.KernelModule;
import com.corona.context.Key;
import com.corona.context.Module;
import com.corona.context.StartModule;
import com.corona.context.Visitor;
import com.corona.context.annotation.Application;
import com.corona.context.annotation.Startup;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>The context manager factory implementation. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ContextManagerFactoryImpl implements ContextManagerFactory {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(ContextManagerFactoryImpl.class);

	/**
	 * all registered scopes
	 */
	private Scopes scopes = new Scopes();
	
	/**
	 * the extension repository
	 */
	private ExtensionRepository extensions = new ExtensionRepository();
	
	/**
	 * all registered component descriptors
	 */
	private Descriptors descriptors = new Descriptors();

	/**
	 * @return all registered component scopes
	 */
	Scopes getScopes() {
		return this.scopes;
	}
	
	/**
	 * @return all registered extension services
	 */
	public ExtensionRepository getExtensions() {
		return extensions;
	}

	/**
	 * @return all registered component descriptors
	 */
	Descriptors getDescriptors() {
		return this.descriptors;
	}
	
	/**
	 * <p>Initialize context manager factory and register all descriptors of component that configured in modules. 
	 * During initializes context manager factory, all descriptor that are defined in modules will be registered into 
	 * this context manager factory in order to be resolved by {@link ContextManager} later. </p>
	 * 
	 * @param modules the modules to be initialized
	 */
	public void init(final Iterable<Module> modules) {
		
		// load all modules and collect all builders by binder
		BinderImpl binder = new BinderImpl(); 
		
		this.logger.info("Load modules to initialize context manager factory");
		for (Module module : ServiceLoader.load(KernelModule.class)) {
			this.logger.debug("Load module [{0}]", module);
			module.configure(binder);
		}
		for (Module module : ServiceLoader.load(StartModule.class)) {
			this.logger.debug("Load module [{0}]", module);
			module.configure(binder);
		}
		for (Module module : modules) {
			this.logger.debug("Load module [{0}]", module);
			module.configure(binder);
		}

		// build all components and scopes that are configured in modules
		this.logger.info("Configure context manager factory with builders");		
		for (Builder<?> builder : binder) {
			
			this.logger.debug("Configure with builder [{0}]", builder);
			try {
				builder.build(this);
			} catch (Throwable e) {
				this.logger.error("Fail to build configuration with builder [{0}], just discard it", builder);
			}
		}
		
		// try to create all application scope component if it is annotated with Startup
		if (this.scopes.get(Application.class) != null) {
			
			final ContextManager contextManager = this.create();
			this.inspect(new Visitor() {
				public void visit(final Key<?> key, final Descriptor<?> descriptor) {
					startup(contextManager, key, descriptor);
				}
			});
		}
	}

	/**
	 * 
	 * @param contextManager the context manager
	 * @param key the component key
	 * @param descriptor the component descriptor
	 */
	private void startup(final ContextManager contextManager, final Key<?> key, final Descriptor<?> descriptor) {
		
		if (!Application.class.equals(descriptor.getScopeType())) {
			return;
		}
		if (!descriptor.getImplementationClass().isAnnotationPresent(Startup.class)) {
			return;
		}
		
		this.logger.debug("Try to create startup application scope component [{0}]", key);
		try {
			contextManager.get(key);
		} catch (Throwable e) {
			this.logger.error("Fail to create component [{0}], just skip it", e, key);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.ContextManagerFactory#getExtension(java.lang.Class, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getExtension(final Class<T> protocolType, final Class<? extends Annotation> annotation) {
		return (T) this.extensions.get(new ExtensionPoint(protocolType, annotation));
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.ContextManagerFactory#getComponentKeys()
	 */
	@Override
	public Key<?>[] getComponentKeys() {
		return this.descriptors.getKeys();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.ContextManagerFactory#getComponentKeys(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> Key<T>[] getComponentKeys(final Class<T> protocolType) {
		
		List<Key<T>> keys = new ArrayList<Key<T>>();
		for (Key<?> key : this.descriptors.getKeys()) {
			if (key.getProtocolType().equals(protocolType)) {
				keys.add((Key<T>) key);
			}
		}
		
		return (Key<T>[]) keys.toArray();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.ContextManagerFactory#getComponentDescriptor(com.corona.context.Key)
	 */
	@Override
	public <T> Descriptor<T> getComponentDescriptor(final Key<T> key) {
		return this.descriptors.get(key);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.ContextManagerFactory#inspect(com.corona.context.Visitor)
	 */
	@Override
	public void inspect(final Visitor visitor) {
		this.descriptors.inspect(visitor);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.ContextManagerFactory#create()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ContextManager create() {
		return this.create(new HashMap<Key, Object>());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.ContextManagerFactory#create(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ContextManager create(final Map<Key, Object> context) {
		
		ContextManagerImpl contextManager = new ContextManagerImpl(this);
		
		// register all application defined component to current context manager
		for (Map.Entry<Key, ?> entry : context.entrySet()) {
			contextManager.getComponents().put(entry.getKey(), entry.getValue());
		}
		
		// register context manager and context manager factory to current context manager
		contextManager.getComponents().put(
				new Key<ContextManager>(ContextManager.class), contextManager
		);
		contextManager.getComponents().put(
				new Key<ContextManagerFactory>(ContextManagerFactory.class), contextManager.getContextManagerFactory()
		);
		
		return contextManager;
	}
}
