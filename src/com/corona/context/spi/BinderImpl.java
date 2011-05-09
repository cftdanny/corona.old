/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.corona.context.Binder;
import com.corona.context.Builder;

/**
 * <p>This binder is used to collect all {@link Builder} that are defined in {@link Module}. After all
 * modules are loaded, context manager factory will use all builders to configure its runtime environment.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class BinderImpl implements Binder, Iterable<Builder<?>> {

	/**
	 * all builders
	 */
	private List<Builder<?>> builders = new ArrayList<Builder<?>>();
	
	/**
	 * <p>Register a new builder to binder repository, and return builder in order to configure other
	 * parameter about this builder. </p>
	 * 
	 * @param builder the new builder
	 * @return the builder
	 */
	public Builder<?> bind(final Builder<?> builder) {
		this.builders.add(builder);
		return builder;
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Builder<?>> iterator() {
		return this.builders.iterator();
	}
}
