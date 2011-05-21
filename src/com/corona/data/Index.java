/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.util.List;

/**
 * <p>This index is used to find or delete entities with values of index. It implements <b>list</b> and 
 * <b>delete</b> methods for every index. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public interface Index<E> {

	/**
	 * close all resources that is allocated for this index
	 */
	void close();
	
	/**
	 * 
	 * @param values the values of index fields
	 * @return the entities that matched with argument values according to index
	 */
	List<E> list(Object... values);
	
	/**
	 * 
	 * @param values the values of index fields
	 * @return how many entities has been deleted with argument values according to index
	 */
	int delete(Object... values);
}
