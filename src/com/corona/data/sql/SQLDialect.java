/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.ResultSet;

import com.corona.data.DataRuntimeException;
import com.corona.data.Dialect;
import com.corona.data.Extractor;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class SQLDialect implements Dialect {

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#createExtractor(java.lang.Object)
	 */
	@Override
	public Extractor createExtractor(final Object result) {
		
		if (!(result instanceof ResultSet)) {
			throw new DataRuntimeException("SQL extractor can only extract value from ResultSet");
		}
		return new SQLExtractor((ResultSet) result);
	}
}
