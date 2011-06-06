/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.util.List;

/**
 * <p>This is the interface for entity home, just like Data Access Object (DAO). It provides CRUD
 * (CREATE, READ, UPDATE and DELETE) function for a database table. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public interface Home<E> {

	/**
	 * release all resources that are allocated for this home
	 */
	void close();
	
	/**
	 * @param id the unique key id
	 * @return the unique key or <code>null</code> if does not exist
	 */
	UniqueKey<E> getUniqueKey(int id);
	
	/**
	 * @param id the index id
	 * @return the index or <code>null</code> if does not exist
	 */
	Index<E> getIndex(int id);
	
	/**
	 * <p>Try to test whether an entity instance (table record) exists in database or not by 
	 * primary key (argument k). </p>
	 * 
	 * @param keys the values of the primary key
	 * @return <code>true</code> if entity with primary key exists
	 */
	boolean exists(Object... keys);
	
	/**
	 * <p>Find an entity instance (table record) from database by primary key (argument k). </p> 
	 * 
	 * @param keys the values of the primary key
	 * @return the entity instance or <code>null</code> if does not exists
	 */
	E get(Object... keys);
	
	/**
	 * <p>update the changed row to data source. If entity is a new entity (does not commit to data
	 * source yet, identity column is null), it will throw an exception.
	 * </p>
	 * 
	 * <p>If passes the changed column names, it will only update these columns. By this, it can improve
	 * updating performance.  
	 * </p>
	 * 
	 * @param e the instance of entity
	 * @return whether entity has been saved to data source
	 */
	boolean update(E e);
	
	/**
	 * <p>Insert a new entity instance into database. Before update, this method will check
	 * whether id of entity is not <code>null</code>. If id is not <code>null</code>, it will 
	 * throw an exception. </p>
	 * 
	 * @param e the entity instance
	 */
	void insert(E e);
	
	/**
	 * <p>Delete an entity instance (table record) from database by primary key (argument k). </p>
	 * 
	 * @param keys the primary key
	 * @return <code>true</code> if this entity has been deleted
	 */
	boolean delete(Object... keys);
	
	/**
	 * @return the statement builder about this entity home
	 */
	StatementBuilder<E> getStatementBuilder();
	
	/**
	 * @return all entity instances (table record) from database
	 */
	List<E> list();
	
	/**
	 * <p>Find a set of entity instances (table record) from database by after FROM [ENTITY] SQL and
	 * its numbered parameters (argument args) from database. </p>
	 * 
	 * <p>NOTES: this method will return a ZERO array event find nothing from database, but never return
	 * <code>null</code> array. </p>
	 * 
	 * @param sql the after FROM [ENTITY] SQL statement
	 * @param args the numbered parameters
	 * @return the array of entity instances
	 */
	List<E> list(String sql, Object... args);
	
	/**
	 * <p>Find a set of entity instances (table record) from database by after FROM [ENTITY] SQL and
	 * its named parameters (argument names and args) from database. </p>
	 * 
	 * <p>NOTES: this method will return a ZERO array event find nothing from database, but never return
	 * <code>null</code> array. </p>
	 * 
	 * @param sql the after FROM [ENTITY] SQL statement
	 * @param names the parameter names
	 * @param args the parameter values
	 * @return the array of entity instances
	 */
	List<E> list(String sql, String[] names, Object[] args);
	
	/**
	 * <p>This method will count how many records in this table. </p>
	 * 
	 * @return how many records in this table
	 */
	long count();
	
	/**
	 * <p>This method will count how many records in this table with a filter argument SQL and its 
	 * parameters. </p>
	 * 
	 * @param filter the after FROM [ENTITY] SQL statement
	 * @param args the parameter values
	 * @return how many records after filter in this table
	 */
	long count(String filter, Object... args);
	
	/**
	 * <p>This method will count how many records in this table with a filter argument SQL and its 
	 * parameters. </p>
	 * 
	 * @param filter the after FROM [ENTITY] SQL statement
	 * @param names the parameter names
	 * @param args the parameter values
	 * @return how many records after filter in this table
	 */
	long count(String filter, String[] names, Object... args);
	
	/**
	 * <p>This method is used to batch update entity instances in database. </p>
	 * 
	 * @param sql the after UPDATE [ENTITY] SQL statement
	 * @param args the numbered parameter values
	 * @return how many records have been updated
	 */
	int update(String sql, Object... args);
	
	/**
	 * <p>This method is used to batch update entity instances in database. </p>
	 * 
	 * @param sql the after UPDATE [ENTITY] SQL statement
	 * @param names the parameter names
	 * @param args the parameter values
	 * @return how many records have been updated
	 */
	int update(String sql, String[] names, Object[] args);
	
	/**
	 * <p>This method is used to batch delete entity instances from database. </p>
	 * 
	 * @param sql the after DELETE FROM [ENTITY] SQL statement
	 * @param args the numbered parameter values
	 * @return how many records has been updated
	 */
	int delete(String sql, Object... args);
	
	/**
	 * <p>This method is used to batch delete entity instances from database. </p>
	 * 
	 * @param sql the after DELETE FROM [ENTITY] SQL statement
	 * @param names the parameter names
	 * @param args the parameter values
	 * @return how many records has been updated
	 */
	int delete(String sql, String[] names, Object[] args);
}
