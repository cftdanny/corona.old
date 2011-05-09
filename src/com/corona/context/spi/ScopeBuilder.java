/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.annotation.Annotation;

import com.corona.context.Builder;
import com.corona.context.ContextManagerFactory;
import com.corona.context.Scope;

/**
 * <p>This builder is used to register a scope that is configured in application defined module. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ScopeBuilder implements Builder<Scope> {

	/**
	 * the annotation type of scope
	 */
	private Class<? extends Annotation> type;
	
	/**
	 * the scope
	 */
	private Scope scope;
	
	/**
	 * @param type the annotation type of scope
	 */
	public ScopeBuilder(final Class<? extends Annotation> type) {
		this.type = type;
	}
	
	/**
	 * @param scope the scope for annotation
	 * @return this scope builder
	 */
	// CHECKSTYLE:OFF
	public ScopeBuilder to(final Scope scope) {
		this.scope = scope;
		return this;
	}
	// CHECKSTYLE:ON
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Builder#build(com.corona.context.ContextManagerFactory)
	 */
	@Override
	public void build(final ContextManagerFactory contextManager) {
		((ContextManagerFactoryImpl) contextManager).getScopes().add(this.type, this.scope);
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Scope (" + this.type.getName() + ")";
	}
}
