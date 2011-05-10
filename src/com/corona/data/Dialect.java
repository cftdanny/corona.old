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
	 * @return the current time from data source, for example, database server
	 */
	Timestamp getTime();
	
	Object getGeneratedId();
}
