/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import com.corona.context.KernelModule;
import com.corona.context.annotation.Application;
import com.corona.context.annotation.Context;
import com.corona.context.annotation.Inject;
import com.corona.context.annotation.Transition;
import com.corona.context.extension.DecoratedConstructorFactory;
import com.corona.context.extension.DecoratedFieldFactory;
import com.corona.context.extension.DecoratedMethodFactory;
import com.corona.context.extension.DecoratedParameterFactory;
import com.corona.context.extension.DecoratedPropertyFactory;

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
		this.bindExtension(DecoratedMethodFactory.class).as(Inject.class).to(
				new InjectDecoratedMethodFactory()
		);
		this.bindExtension(DecoratedFieldFactory.class).as(Inject.class).to(
				new InjectDecoratedFieldFactory()
		);
		this.bindExtension(DecoratedParameterFactory.class).as(Inject.class).to(
				new InjectDecoratedParameterFactory()
		);
		this.bindExtension(DecoratedPropertyFactory.class).as(Inject.class).to(
				new InjectDecoratedPropertyFactory()
		);
		this.bindExtension(DecoratedConstructorFactory.class).as(Inject.class).to(
				new InjectDecoratedConstructorFactory()
		);
	}
}
