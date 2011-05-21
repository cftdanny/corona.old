/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;
import com.corona.servlet.Producer;
import com.corona.servlet.ProducerFactory;
import com.corona.servlet.annotation.FreeMaker;

/**
 * <p>This producer is used to create HTTP response by <b>FreeMaker</b> template and outcome
 * from an injection method of component </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class FreeMakerProducerFactory implements ProducerFactory<FreeMaker> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.ProducerFactory#create(
	 * 	com.corona.context.Key, com.corona.context.extension.DecoratedMethod
	 * )
	 */
	@Override
	public Producer create(final Key<?> key, final DecoratedMethod method) {
		return new FreeMakerProducer(key, method);
	}
}
