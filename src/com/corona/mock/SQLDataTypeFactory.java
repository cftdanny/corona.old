/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.mock;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.hsqldb.types.Types;

/**
 * <p>This data type factory is used to support some customized data type for DbUnit, for example, SQL Date. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class SQLDataTypeFactory extends HsqldbDataTypeFactory {

	/**
	 * {@inheritDoc}
	 * @see org.dbunit.dataset.datatype.DefaultDataTypeFactory#createDataType(int, java.lang.String)
	 */
	@Override
	public DataType createDataType(final int sqlType, final String sqlTypeName) throws DataTypeException {
		
		// FOR SQL STRING
		if (Types.CHAR == sqlType) {
			return new SQLStringType("CHAR", Types.CHAR);
		}
		if (Types.VARCHAR == sqlType) {
			return new SQLStringType("VARCHAR", Types.VARCHAR);
		}
		if (Types.LONGVARCHAR == sqlType) {
			return new SQLStringType("LONGVARCHAR", Types.LONGVARCHAR);
		}
		if (Types.NCHAR == sqlType) {
			return new SQLStringType("NCHAR", Types.NCHAR);
		}
		if (Types.NVARCHAR == sqlType) {
			return new SQLStringType("NVARCHAR", Types.NVARCHAR);
		}
		if (Types.LONGNVARCHAR == sqlType) {
			return new SQLStringType("LONGNVARCHAR", Types.LONGNVARCHAR);
		}
		
		// FOR SQL DATE
		if (Types.DATE == sqlType) {
			return new SQLDateType();
		}
		
		// OTHERS
		return super.createDataType(sqlType, sqlTypeName);
	}
}
