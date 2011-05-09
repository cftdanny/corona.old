/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.xml;

import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;
import com.corona.servlet.Producer;
import com.corona.servlet.ProducerFactory;
import com.corona.servlet.annotation.Xml;

/**
 * <p>This factory is used to create JSON producer by a method that is annotated with {@link Json}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class XmlProducerFactory implements ProducerFactory<Xml> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.ProducerFactory#create(
	 * 	com.corona.context.Key, com.corona.context.extension.DecoratedMethod
	 * )
	 */
	@Override
	public Producer create(final Key<?> key, final DecoratedMethod method) {
		return new XmlProducer(key, method);
	}
}
