/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This builder is used to build query and command that will be used in data access object {@link Home}. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public interface StatementBuilder<E> {

	/**
	 * @return the command is used to insert entity to data source
	 */
	Command createInsertCommand();
	
	/**
	 * @param filter the filter
	 * @return the query is used to count rows in data source by filter
	 */
	Query<Long> createCountQuery(String filter);
	
	/**
	 * @param filter the filter
	 * @return the query to list rows in data source by filter
	 */
	Query<E> createListQuery(String filter);
	
	/**
	 * @param filter the filter
	 * @return the DELETE command
	 */
	Command createDeleteCommand(String filter);
	
	/**
	 * @param filter the filter
	 * @return the UPDATE command
	 */
	Command createUpdateCommand(String filter);
}
