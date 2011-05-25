/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.excel;

import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;
import com.corona.servlet.Producer;
import com.corona.servlet.ProducerFactory;
import com.corona.servlet.annotation.Excel;

/**
 * <p>This producer is used to produce context by <b>iText</b> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ExcelProducerFactory implements ProducerFactory<Excel> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.ProducerFactory#create(
	 * 	com.corona.context.Key, com.corona.context.extension.DecoratedMethod
	 * )
	 */
	@Override
	public Producer create(final Key<?> key, final DecoratedMethod method) {
		return new ExcelProducer(key, method);
	}
}
