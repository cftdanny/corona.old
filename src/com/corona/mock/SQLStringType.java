/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.mock;

import org.dbunit.dataset.datatype.StringDataType;
import org.dbunit.dataset.datatype.TypeCastException;

/**
 * <p>This String data type will compare to strings, and before compare, it will trim all spaces after 
 * 2 string values. Usually, it is because some database servers, for example, DB2 or HSQLDB will add
 * some additional spaces for String data. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLStringType extends StringDataType {

	/**
	 * @param name the name
	 * @param sqlType the SQL type
	 */
	public SQLStringType(final String name, final int sqlType) {
		super(name, sqlType);
	}

	/**
	 * {@inheritDoc}
	 * @see org.dbunit.dataset.datatype.AbstractDataType#compareNonNulls(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected int compareNonNulls(final Object value1, final Object value2) throws TypeCastException {
		return value1.toString().trim().compareTo(value2.toString().trim());
	}
}
