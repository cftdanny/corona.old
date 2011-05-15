/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <D> the type of query result
 * @param <E> the type of entity
 */
public interface Handler<D, E> {

	/**
	 * <p>Transfer current row in query result to entity class.
	 * </p>
	 *  
	 * @param result the query result from data source
	 * @return the mapped entity
	 */
	E get(D result);
}
