/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;

/**
 * <p>The helper {@link Producer} that store key and annotated method. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractProducer implements Producer {

	/**
	 * the component key
	 */
	private Key<?> key;
	
	/**
	 * the annotated producer method
	 */
	private DecoratedMethod method;
	
	/**
	 * @param key the component key
	 * @param method the annotated producer method
	 */
	public AbstractProducer(final Key<?> key, final DecoratedMethod method) {
		this.key = key;
		this.method = method;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Producer#getKey()
	 */
	@Override
	public Key<?> getKey() {
		return this.key;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Producer#getDecoratedMethod()
	 */
	@Override
	public DecoratedMethod getDecoratedMethod() {
		return this.method;
	}
}
