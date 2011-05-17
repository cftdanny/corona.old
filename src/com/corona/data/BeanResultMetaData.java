/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This class is used to store configuration in order to map row of query result of class instance. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of result class that query result can be mapped to
 */
public class BeanResultMetaData<E> extends AbstractResultMetaData<E> {

	/**
	 * @param resultClass the class that is used to map query result to 
	 */
	public BeanResultMetaData(final Class<E> resultClass) {
		super(resultClass);
	}
}
