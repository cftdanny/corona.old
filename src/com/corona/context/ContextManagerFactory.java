/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <p>The context manager factory is used to configure context runtime environment according to all customized 
 * <b>{@link Module}</b> and create <b>{@link ContextManager}. </b>.
 * </p>
 * 
 * <p>Usually, there is only one instance of context manager factory for one application, and
 * more than one context managers for an atom job (job of database transaction, job of HTTP 
 * request, etc).
 * </p> 
 *
 * @author $Author$
 * @version $Id$
 */
public interface ContextManagerFactory {

	/**
	 * <p>Find the registered extension instance by protocol type and annotation type. </p>
	 * 
	 * @param <T> the protocol type
	 * @param protocolType the protocol type of extension
	 * @param annotation the annotation type of extension
	 * @return the extension instance or <code>null</code> if does not exist
	 */
	<T> T getExtension(Class<T> protocolType, Class<? extends Annotation> annotation);
	
	/**
	 * @return all component keys in context manager factory
	 */
	Key<?>[] getComponentKeys();
	
	/**
	 * <p>Get all component keys with same injection (protocol type). </p>
	 * 
	 * @param <T> the injection type
	 * @param protocolType the protocol type
	 * @return all component keys with specified protocol type
	 */
	<T> Key<T>[] getComponentKeys(Class<T> protocolType);
	
	/**
	 * <p>Get component descriptor by key. </p>
	 * 
	 * @param <T> the injection type of component
	 * @param key the component key
	 * @return the component descriptor
	 */
	<T> Descriptor<T> getComponentDescriptor(Key<T> key);
	
	/**
	 * <p>Inspect all component configurations by a visitor. </p>
	 * 
	 * @param visitor the visitor
	 */
	void inspect(Visitor visitor);
	
	/**
	 * <p>Create a new context manager with current context manager factory {@link ContextManagerFactory}, 
	 * and every context manager manages its own context, the components in a context will be created 
	 * when it is first visited. </p>
	 * 
	 * @return the new context manager
	 */
	ContextManager create();

	/**
	 * <p>Create a new context manager with current context manager factory {@link ContextManagerFactory}, 
	 * and every context manager manages its own context, the components in a context will be created 
	 * when it is first visited. </p>
	 * 
	 * @param context the components context that will merge into context manager
	 * @return the new context manager
	 */
	ContextManager create(@SuppressWarnings("rawtypes") Map<Key, Object> context);
	
	/**
	 * close this context manager factory
	 */
	void close();
}
