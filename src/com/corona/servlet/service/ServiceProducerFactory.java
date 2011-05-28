/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.service;

import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;
import com.corona.servlet.Producer;
import com.corona.servlet.ProducerFactory;
import com.corona.servlet.annotation.Service;

/**
 * <p>This factory is used to create {@link ServiceProducer} by method that is annotated with {@link Service}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ServiceProducerFactory implements ProducerFactory<Service> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.ProducerFactory#create(
	 * 	com.corona.context.Key, com.corona.context.extension.DecoratedMethod
	 * )
	 */
	@Override
	public Producer create(final Key<?> key, final DecoratedMethod method) {
		return new ServiceProducer(key, method);
	}
}