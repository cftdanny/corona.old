/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.redirect;

import com.corona.context.InjectMethod;
import com.corona.context.Key;
import com.corona.servlet.Producer;
import com.corona.servlet.ProducerFactory;
import com.corona.servlet.annotation.Redirect;

/**
 * <p>This producer is used to produce context by <b>iText</b> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class RedirectProducerFactory implements ProducerFactory<Redirect> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.ProducerFactory#create(
	 * 	com.corona.context.Key, com.corona.context.InjectMethod
	 * )
	 */
	@Override
	public Producer create(final Key<?> key, final InjectMethod method) {
		return new RedirectProducer(key, method);
	}
}
