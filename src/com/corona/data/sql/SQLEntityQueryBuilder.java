/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.EntityMetaData;
import com.corona.data.EntityQueryBuilder;

/**
 * <p>The generic entity query builder for SQL database. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class SQLEntityQueryBuilder implements EntityQueryBuilder {

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityQueryBuilder#build(com.corona.data.EntityMetaData, java.lang.String)
	 */
	@Override
	public String build(final EntityMetaData<?> entityMetaData, final String filter) {
		return null;
	}
}
