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
 * @param <E> the table entity data type
 */
public interface Home<E> {

	/**
	 * <p>Try to test whether an entity instance (table record) exists in database or not by 
	 * primary key (argument k). </p>
	 * 
	 * @param pks the values of the primary key
	 * @return <code>true</code> if entity with primary key exists
	 */
	boolean exists(Object... pks);
	
	/**
	 * <p>Find an entity instance (table record) from database by primary key (argument k). </p> 
	 * 
	 * @param pks the values of the primary key
	 * @return the entity instance or <code>null</code> if does not exists
	 */
	E get(Object... pks);
	
	/**
	 * <p>Update a changed entity instance into database. Before update, this method will check
	 * whether id of entity is <code>null</code>. If id is <code>null</code>, it will throw an
	 * exception. </p>
	 *  
	 * @param e the entity instance
	 * @return whether this record has been updated
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
	 * @param pks the primary key
	 * @return <code>true</code> if this entity has been deleted
	 */
	boolean delete(Object... pks);
	
	/**
	 * <p>Delete an entity instance (table record) from database by entity instance. </p>
	 * 
	 * @param e the entity instance
	 * @return <code>true</code> if this entity has been deleted
	 */
	boolean delete(E e);
	
	/**
	 * <p>Execute query statement and return result as entity instance. If more than one records
	 * fetched, this method will throw an exception. </p>
	 * 
	 * @param sql the after FROM [ENTITY] SQL statement 
	 * @param args the numbered parameters
	 * @return the entity instance
	 */
	E single(String sql, Object... args);
	
	/**
	 * <p>Execute query statement and return result as entity instance. If more than one records
	 * fetched, this method will throw an exception. </p>
	 * 
	 * @param sql the after FROM [ENTITY] SQL statement 
	 * @param names the parameter names
	 * @param args the numbered parameters
	 * @return the entity instance
	 */
	E single(String sql, String[] names, Object[] args);
	
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
	 * @param sql the after FROM [ENTITY] SQL statement
	 * @param args the parameter values
	 * @return how many records after filter in this table
	 */
	long count(String sql, Object... args);
	
	/**
	 * <p>This method will count how many records in this table with a filter argument SQL and its 
	 * parameters. </p>
	 * 
	 * @param sql the after FROM [ENTITY] SQL statement
	 * @param names the parameter names
	 * @param args the parameter values
	 * @return how many records after filter in this table
	 */
	long count(String sql, String[] names, Object... args);
	
	/**
	 * <p>This method is used to fetch an entity instance from database by primary key. </p>
	 *  
	 * @param unique the unique key index
	 * @param args the values of unique key
	 * @return the entity instance or <code>null</code> if does not exist
	 */
	E uget(int unique, Object... args);
	
	/**
	 * <p>This method is used to check whether an entity instance exists in database or not by 
	 * primary key. </p>
	 * 
	 * @param uk the unique key index
	 * @param args the values of unique key
	 * @return <code>true</code> if entity instance exists
	 */
	boolean uexists(int uk, Object... args);
	
	/**
	 * <p>This method is used to delete an entity instance from database by primary key. </p>
	 * 
	 * @param uk the unique key index
	 * @param args the values of unique key
	 * @return <code>true</code> if the record has been deleted
	 */
	boolean udelete(int uk, Object... args);
	
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
