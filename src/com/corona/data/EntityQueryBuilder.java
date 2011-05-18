/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This builder is used to build query statement for an entity with filter. This builder is used
 * in {@link Home} to filter entities. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface EntityQueryBuilder {

	/**
	 * @param entityMetaData the entity configuration information
	 * @param filter the filter statement
	 * @return the query statement
	 */
	String build(EntityMetaData<?> entityMetaData, String filter);
}
