/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.sql.Timestamp;

/**
 * <p>The dialect that works with specified data source, for example, MySQL Server, DB2, Oracle, etc. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Dialect {

	/**
	 * @return the current time from data source, for example, from database server
	 */
	Timestamp getCurrentTime();
	
	/**
	 * @param command the command that is just executed before
	 * @return the new generated keys
	 */
	Object[] getGeneratedKeys(Command command);
	
	/**
	 * @param result the query result that returns by query for specified data source
	 * @return the column value extractor
	 */
	ResultHolder getResultHolder(Object result);
	
	/**
	 * @return the entity query builder
	 */
	EntityQueryBuilder getEntityQueryBuilder();

	/**
	 * @return the entity delete builder
	 */
	EntityDeleteBuilder getEntityDeleteBuilder();

	/**
	 * @return the entity update builder
	 */
	EntityUpdateBuilder getEntityUpdateBuilder();
}
