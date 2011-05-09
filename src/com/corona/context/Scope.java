/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

/**
 * <p>The scope will store all components for its scope. It will implement how to get component from
 * its scope. And context will call <b>enter</b> and <b>exit</b> when it try to use or don't use this
 * scope. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Scope {

	/**
	 * <p>Resolve a component from this scope by its key, but if key isn't registered to this scope, just 
	 * return <b>null</b>. </p>
	 * 
	 * <p>Usually, component with key is created when it is first visited; after it is created, component
	 * is just get from scope repository without creation again. 
	 * </p>
	 * 
	 * @param <T> the injection type
	 * @param context the current context
	 * @param key the component key
	 * @return the component or <b>null</b> if it doesn't belong to scope
	 */
	<T> T get(ContextManager context, Key<T> key);
}
