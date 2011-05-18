/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This builder is used to build UPDATE statement for an entity with filter. This builder will be used
 * in {@link Home} to batch update entities in database. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface EntityUpdateBuilder {

	/**
	 * @param config the entity configuration information
	 * @param filter the filter statement
	 * @return the UPDATE SQL statement
	 */
	String build(EntityMetaData<?> config, String filter);
}
