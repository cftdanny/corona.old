/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface QueryBuilder {

	/**
	 * 
	 * @param entityName the entity name
	 * @param filter
	 * @return
	 */
	String build(String entityName, String filter);
}
