/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.mock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.TypeCastException;

/**
 * <p>The SQL Date type. It will add 00:00:00 to date string before cast it to String. </p>
 * 
 * @author $Author$
 * @version $Id$
 */
public class SQLDateType extends AbstractDataType {

	/**
	 * the date pattern
	 */
	private static final SimpleDateFormat PATTERN = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * create SQL Date type
	 */
	SQLDateType() {
		super("DATE", Types.DATE, java.sql.Date.class, false);
	}

	/**
	 * {@inheritDoc}
	 * @see org.dbunit.dataset.datatype.DataType#typeCast(java.lang.Object)
	 */
	public Object typeCast(final Object value) throws TypeCastException {
		
		if (value == null || value == ITable.NO_VALUE) {
			return null;
		}

		if (value instanceof java.sql.Date) {
			return value;
		}

		if (value instanceof java.util.Date) {
            return new java.sql.Date(((java.util.Date) value).getTime());
		}

		if (value instanceof Long) {
            return new java.sql.Date((Long) value);
		}

		if (value instanceof String) {
			
			String stringValue = ((String) value).trim();
			if (stringValue.length() == 10) {
				stringValue = stringValue + " 00:00:00";
			}
			
			try {
				java.util.Date date = PATTERN.parse(stringValue);
                return new java.sql.Date(date.getTime());
			} catch (Exception e) {
				throw new TypeCastException(value, this, e);
			}
		}

		throw new TypeCastException(value, this);
	}

	/**
	 * {@inheritDoc}
	 * @see org.dbunit.dataset.datatype.AbstractDataType#isDateTime()
	 */
	public boolean isDateTime() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.dbunit.dataset.datatype.AbstractDataType#getSqlValue(int, java.sql.ResultSet)
	 */
	public Object getSqlValue(final int column, final ResultSet resultSet) throws SQLException, TypeCastException {

		java.sql.Date value = resultSet.getDate(column);
		if (value == null || resultSet.wasNull()) {
			return null;
		}
		return value;
	}

	/**
	 * {@inheritDoc}
	 * @see org.dbunit.dataset.datatype.AbstractDataType#setSqlValue(java.lang.Object, int, java.sql.PreparedStatement)
	 */
	public void setSqlValue(
			final Object value, final int column, final PreparedStatement statement
	) throws SQLException, TypeCastException {
		statement.setDate(column, (java.sql.Date) typeCast(value));
	}

	/**
	 * {@inheritDoc}
	 * @see org.dbunit.dataset.datatype.AbstractDataType#compareNonNulls(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected int compareNonNulls(final Object value1, final Object value2) throws TypeCastException {
		
		if (!((value1 instanceof java.util.Date) && (value2 instanceof java.util.Date))) {
			return super.compareNonNulls(value1, value2);
		}
		
		// create calendar according to date of value 1
		Calendar c1 = Calendar.getInstance();
		c1.setTime((java.util.Date) value1);

		// create calendar according to date of value 2
		Calendar c2 = Calendar.getInstance();
		c2.setTime((java.util.Date) value2);
		
		// compare the era, e.g., AD or BC in the Julian calendar of 2 calendars
		int result = c1.get(Calendar.ERA) - c2.get(Calendar.ERA);
		if (result != 0) {
			return result;
		}
		
		// compare year of 2 calendars
		result = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
		if (result != 0) {
			return result;
		}
		
		// compare day of year of 2 calendars
		return c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);
	}
}