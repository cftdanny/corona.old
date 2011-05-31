/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.reflect.Method;

import com.corona.context.AbstractInjectProperty;
import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.annotation.Inject;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to register a setter method that is annotated with injection annotation. 
 * Its value will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class DefaultInjectProperty extends AbstractInjectProperty {
	
	/**
	 * the name
	 */
	private String name = null;
 
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param property the property that is annotated with {@link Inject}
	 */
	DefaultInjectProperty(final ContextManagerFactory contextManagerFactory, final Method property) {
		super(contextManagerFactory, property);
		this.name = this.getMethod().getAnnotation(Inject.class).value();
		if (StringUtil.isBlank(this.name)) {
			this.name = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractInjectProperty#get(com.corona.context.ContextManager)
	 */
	@Override
	protected Object get(final ContextManager contextManager) {
		return contextManager.get(this.getType(), this.name);
	}
}
