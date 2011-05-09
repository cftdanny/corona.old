/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;


/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ConnectionManagerFactory {

	/**
	 * @return the database family (MySQL, SQL Server, DB2, etc)
	 */
	String getFamily();
	
	ConnectionManager open();
}
