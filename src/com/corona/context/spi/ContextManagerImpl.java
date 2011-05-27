/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.util.HashMap;
import java.util.Map;

import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.Descriptor;
import com.corona.context.Key;
import com.corona.context.Scope;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>The context manager implementation. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ContextManagerImpl implements ContextManager {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(ContextManagerImpl.class);
	
	/**
	 * the parent context manager factory
	 */
	private ContextManagerFactoryImpl contextManagerFactory;
	
	/**
	 * all cached components repository
	 */
	private Map<Key<?>, Object> components = new HashMap<Key<?>, Object>();
	
	/**
	 * @param contextManagerFactory the parent context manager factory
	 */
	ContextManagerImpl(final ContextManagerFactoryImpl contextManagerFactory) {
		this.contextManagerFactory = contextManagerFactory;
		
		// Store context manager and its factory, in order to fast inject and better performance
		this.components.put(new Key<ContextManager>(ContextManager.class), this);
		this.components.put(
				new Key<ContextManagerFactory>(ContextManagerFactory.class), contextManagerFactory
		);
	}

	/**
	 * @param <T> the injection type of component
	 * @param key the component key
	 * @return the component descriptor or <code>null</code> if does not exists
	 */
	<T> Descriptor<T> getDescriptor(final Key<T> key) {
		return this.contextManagerFactory.getDescriptors().get(key);
	}
	
	/**
	 * @return all cached components in context manager
	 */
	Map<Key<?>, Object> getComponents() {
		return this.components;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.ContextManager#close()
	 */
	@Override
	public void close() {
		// TODO: XXX
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.ContextManager#get(com.corona.context.Key)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(final Key<T> key) {
		
		// try to find predefined component by key in context. If exists, return it 
		if (this.components.containsKey(key)) {
			return (T) this.components.get(key);
		}
		
		// get component descriptor from context manager factory
		Descriptor<T> descriptor = this.contextManagerFactory.getDescriptors().get(key);
		if (descriptor == null) {
			this.logger.error("Component with key [{0}] does not exists", key.toString());
			throw new ConfigurationException("Component with key [{0}] does not exists", key.toString());
		}
		
		// find scope about component, will use this scope to resolve component instance
		Scope scope = this.contextManagerFactory.getScopes().get(descriptor.getScopeType());
		if (scope == null) {
			this.logger.error("Scope with annotation type [{0}] does not exists", descriptor.getScopeType());
			throw new ConfigurationException(
					"Scope with annotation type [{0}] does not exists", descriptor.getScopeType()
			);
		}
		
		return scope.get(this, key);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.ContextManager#get(java.lang.Class)
	 */
	@Override
	public <T> T get(final Class<T> type) {
		return this.get(new Key<T>(type));
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.ContextManager#get(java.lang.Class, java.lang.String)
	 */
	@Override
	public <T> T get(final Class<T> type, final String name) {
		return this.get(new Key<T>(type, name));
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.ContextManager#get(java.lang.String)
	 */
	@Override
	public Object get(final String alias) {
		
		Key<?> key = this.contextManagerFactory.getDescriptors().get(alias);
		return (key != null) ? this.get(key) : null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.ContextManager#getContextManagerFactory()
	 */
	@Override
	public ContextManagerFactory getContextManagerFactory() {
		return this.contextManagerFactory;
	}
}
