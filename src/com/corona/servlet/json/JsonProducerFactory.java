/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.json;

import com.corona.context.InjectMethod;
import com.corona.context.Key;
import com.corona.servlet.Producer;
import com.corona.servlet.ProducerFactory;
import com.corona.servlet.annotation.Json;

/**
 * <p>This factory is used to create JSON producer by a method that is annotated with {@link Json}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class JsonProducerFactory implements ProducerFactory<Json> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.ProducerFactory#create(
	 * 	com.corona.context.Key, com.corona.context.InjectMethod
	 * )
	 */
	@Override
	public Producer create(final Key<?> key, final InjectMethod method) {
		return new JsonProducer(key, method);
	}
}
