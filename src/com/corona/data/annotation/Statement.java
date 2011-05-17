/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.annotation;

/**
 * <p>the query or command script. If data source is database, it can be SQL statement. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public @interface Statement {

	/**
	 * the default data source family. For example, MySQL, DB2, etc
	 */
	String family();
	
	/**
	 * the query or command statement. For example, database is SQL 
	 */
	String statement();
}
