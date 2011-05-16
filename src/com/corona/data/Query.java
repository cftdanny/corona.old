/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.util.List;

/**
 * <p>This class </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public interface Query<E> {

	/**
	 * @return the delegate statement
	 */
	Object getSource();
	
	/**
	 * <p>Set the max rows return after executes this query. If returned rows exceed this value,
	 * the exceeded rows will be discarded. </p> 
	 * 
	 * @param maxResult the max rows will returns
	 * @return this query
	 */
	Query<E> setMaxResult(int maxResult); 
	
	/**
	 * <p>Close this query. For example, in SQL, it can be used to close prepared statement. </p>
	 */
	void close();

	/**
	 * <p>Execute SQL and just return one row, and map this row to a java bean. If there are more 
	 * than one rows return, will throw an exception. </p>
	 * 
	 * @param args the argument values of SQL
	 * @return the result bean
	 */
	E get(Object... args);

	/**
	 * <p>Execute SQL and just return one row, and map this row to a java bean. If there are more 
	 * than one rows return, will throw an exception. </p>
	 *
	 * @param names the argument names of SQL
	 * @param args the argument values of SQL
	 * @return the result bean
	 */
	E get(String[] names, Object[] args);
	
	/**
	 * <p>Execute SQL and map the result to list of java beans. </p>
	 * 
	 * @param args the argument values of SQL
	 * @return the result beans
	 */
	List<E> list(Object... args);
	
	/**
	 * <p>Execute SQL and map the result to list of java beans. </p>
	 * 
	 * @param names the argument names of SQL
	 * @param args the argument values of SQL
	 * @return the result beans
	 */
	List<E> list(String[] names, Object[] args);
}
