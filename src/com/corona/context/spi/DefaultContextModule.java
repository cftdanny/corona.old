/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import com.corona.context.InjectConstructorFactory;
import com.corona.context.InjectFieldFactory;
import com.corona.context.InjectParameterFactory;
import com.corona.context.InjectMethodFactory;
import com.corona.context.InjectPropertyFactory;
import com.corona.context.KernelModule;
import com.corona.context.annotation.Application;
import com.corona.context.annotation.Context;
import com.corona.context.annotation.Inject;
import com.corona.context.annotation.Transition;

/**
 * <p>The internal module. It is used to register all internal scope, injection, etc. When initial
 * context manager factory, this module will be loaded first. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DefaultContextModule extends KernelModule {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		// register all built-in scopes and scope component resolver
		this.bindScope(Application.class).to(new ApplicationScope());
		this.bindScope(Context.class).to(new ContextScope());
		this.bindScope(Transition.class).to(new TransitionScope());

		// register all built-in extensions that are defined in framework
		this.bindExtension(InjectMethodFactory.class).as(Inject.class).to(
				new DefaultInjectMethodFactory()
		);
		this.bindExtension(InjectFieldFactory.class).as(Inject.class).to(
				new DefaultInjectFieldFactory()
		);
		this.bindExtension(InjectParameterFactory.class).as(Inject.class).to(
				new DefaultInjectParameterFactory()
		);
		this.bindExtension(InjectPropertyFactory.class).as(Inject.class).to(
				new DefaultInjectPropertyFactory()
		);
		this.bindExtension(InjectConstructorFactory.class).as(Inject.class).to(
				new DefaultInjectConstructorFactory()
		);
	}
}
