/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>This handler is used to transfer query result to list by <b>get</b> method. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of query result entity
 */
public abstract class AbstractResultHandler<E> implements ResultHandler<E> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHandler#toList(com.corona.data.ResultHolder)
	 */
	@Override
	public List<E> toList(final ResultHolder result) {
		
		List<E> outcome = new ArrayList<E>();
		while (result.next()) {
			outcome.add(this.get(result));
		}
		return outcome;
	}
}
