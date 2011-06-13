/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.data;

import com.corona.data.annotation.NamedQueries;
import com.corona.data.annotation.NamedQuery;

/**
 * <p>A test query </p>
 *
 * @author $Author$
 * @version $Id$
 */
@NamedQuery("SELECT * FROM TORDMST")
@NamedQueries(
		@NamedQuery(name = "test", value = "SELECT * FROM TORDMST")
)
public class SQLStatementPoolQuery extends TORDMST {

}
