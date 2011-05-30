/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.chart;

import com.corona.context.InjectMethod;
import com.corona.context.Key;
import com.corona.servlet.Producer;
import com.corona.servlet.ProducerFactory;
import com.corona.servlet.annotation.Chart;

/**
 * <p>This producer is used to produce context by <b>JFreeChart</b> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ChartProducerFactory implements ProducerFactory<Chart> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.ProducerFactory#create(
	 * 	com.corona.context.Key, com.corona.context.InjectMethod
	 * )
	 */
	@Override
	public Producer create(final Key<?> key, final InjectMethod method) {
		return new ChartProducer(key, method);
	}
}
