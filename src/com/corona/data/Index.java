/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>The index is used to manage table entity with index. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of table entity
 */
public interface Index<E> {

	/**
	 * 
	 * @param values the values of index fields
	 * @return the entities that matched with argument values according to index
	 */
	E[] list(Object... values);
	
	/**
	 * 
	 * @param values the values of index fields
	 * @return how many entities has been deleted with argument values according to index
	 */
	int delete(Object... values);
}
