/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import com.corona.context.Binder;
import com.corona.context.Builder;
import com.corona.context.ContextManagerFactory;
import com.corona.context.Module;

/**
 * <p>This builder is used to bind a group of predefined and related components in a module to context. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ModuleBuilder implements Builder<Module> {

	/**
	 * the binder
	 */
	private Binder binder;
	
	/**
	 * @param binder the binder
	 * @param module the predefined module
	 */
	public ModuleBuilder(final Binder binder, final Module module) {
		this.binder = binder;
		module.configure(this.binder);
	}
	
	/**
	 * @param module the predefined module
	 * @return this module builder
	 */
	public ModuleBuilder with(final Module module) {
		module.configure(this.binder);
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Builder#build(com.corona.context.ContextManagerFactory)
	 */
	@Override
	public void build(final ContextManagerFactory contextManagerFactory) {
		// do nothing, have configured before
	}
}
