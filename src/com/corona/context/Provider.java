/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

/**
 * <p>The provider is used to create component at runtime by application. Usually, the component
 * should be a dynamic instance. </p>
 * 
 * <p>All provider components should implement {@link Provider} interface. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the injection type
 */
public interface Provider<T> {

	/**
	 * @return the component instance
	 */
	T get();
}
