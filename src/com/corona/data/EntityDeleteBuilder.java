/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This builder is used to build DELETE statement for an entity with filter. This builder will be used
 * in {@link Home} to batch delete entities from database. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface EntityDeleteBuilder {

	/**
	 * @param entityMetaData the entity configuration information
	 * @param filter the filter statement
	 * @return the DELETE statement
	 */
	String build(EntityMetaData<?> entityMetaData, String filter);
}
