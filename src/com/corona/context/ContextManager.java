/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

/**
 * <p>The context manager is use to resolve component instance by component key, create component instance
 * and inject all required value after component is created.
 * </p>
 * 
 * <p>Context manager is starting point for development. When needs component, it needs create context 
 * manager from context manager factory first, then resolve component instance. For example:
 * </p>
 * 
 * <pre>
 * 	ContextManagerFactory contextManagerFactory = Initializer.create(module1, module2, module3);
 * 	ContextManager contextManager = contextManagerFactory.create();
 * 	Component component = contextManager.get(Component.class);
 * 	// do something with component
 * 	component.close();
 * </pre>
 * 
 * <p>After context manager is used, it needs to be closed to release the resources it allocated before. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ContextManager {

	/**
	 * @return the parent context manager factory that creates current context manager
	 */
	ContextManagerFactory getContextManagerFactory();
	
	/**
	 * <p>Resolve component instance by the given key from current context manager. If component key is not
	 * registered, it throw {@link CreationException}. </p>
	 * 
	 * @param <T> the injection type
	 * @param key the component key
	 * @return the component instance
	 * @throws CreationException
	 */
	<T> T get(Key<T> key);
	
	/**
	 * <p>Resolve component instance by the given injection type as key from current context manager. 
	 * If component key is not registered, it throw {@link CreationException}. </p>
	 * 
	 * @param <T> the injection type
	 * @param type the injection type of component
	 * @return the component instance
	 * @throws CreationException
	 */
	<T> T get(Class<T> type);
	
	/**
	 * <p>Resolve component instance by the given injection type and component name as key from current 
	 * context manager. If component key is not registered, it throw {@link CreationException}. </p>
	 * 
	 * @param <T> the injection type
	 * @param type the injection type of component
	 * @param name the component name
	 * @return the component instance
	 * @throws CreationException
	 */
	<T> T get(Class<T> type, String name);

	/**
	 * <p>Resolve component by its alias name. If alias does not exist, just return <code>null</code>. </p>
	 * 
	 * @param alias the component alias
	 * @return the component instance or <code>null</code> if alias does not exist
	 */
	Object get(String alias);
}
