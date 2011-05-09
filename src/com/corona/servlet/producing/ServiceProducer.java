/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing;

import java.io.OutputStream;

import com.corona.context.ContextManager;
import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;
import com.corona.servlet.AbstractProducer;
import com.corona.servlet.ProduceException;

/**
 * <p>This producer is used to create HTTP response by annotated method in component. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ServiceProducer extends AbstractProducer {

	/**
	 * @param key the component key
	 * @param method the annotated producer method
	 */
	public ServiceProducer(final Key<?> key, final DecoratedMethod method) {
		super(key, method);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Producer#produce(
	 * 	com.corona.context.ContextManager, java.lang.Object, java.io.OutputStream
	 * )
	 */
	@Override
	public void produce(
			final ContextManager managerManager, final Object root, final OutputStream out
	) throws ProduceException {
	}
}
