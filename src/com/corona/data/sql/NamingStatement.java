/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * <p>This statement is used to execute named parameterized SQL statement. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class NamingStatement extends PreparedStatementWrapper {

	/**
	 * the named parameterized SQL statement parser
	 */
	private SQLParser parser;
	
	/**
	 * @param connection the opened JDBC connection
	 * @param sql the SQL statement
	 * @exception SQLException if fail to prepare SQL statement
	 */
	NamingStatement(final Connection connection, final String sql) throws SQLException {
		
		this.parser = new SQLParser(sql);
		this.setPreparedStatement(connection.prepareStatement(this.parser.getClause()));
	}
	
	/**
	 * @param parameterName the parameter name
	 * @return the parameter index
	 */
	private int getParameterIndex(final String parameterName) {
		return this.parser.get(parameterName);
	}

	/**
	 * @param parameterName the parameter name
	 * @param sqlType the SQL type
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setNull(final String parameterName, final int sqlType) throws SQLException {
		this.setNull(this.getParameterIndex(parameterName), sqlType);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setBoolean(final String parameterName, final boolean x) throws SQLException {
		this.setBoolean(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setByte(final String parameterName, final byte x) throws SQLException {
		this.setByte(this.getParameterIndex(parameterName), x);
	}
	
	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setShort(final String parameterName, final short x) throws SQLException {
		this.setShort(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setInt(final String parameterName, final int x) throws SQLException {
		this.setInt(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setLong(final String parameterName, final long x) throws SQLException {
		this.setLong(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setFloat(final String parameterName, final float x) throws SQLException {
		this.setFloat(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setDouble(final String parameterName, final double x) throws SQLException {
		this.setDouble(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setBigDecimal(final String parameterName, final BigDecimal x) throws SQLException {
		this.setBigDecimal(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setString(final String parameterName, final String x) throws SQLException {
		this.setString(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setBytes(final String parameterName, final byte[] x) throws SQLException {
		this.setBytes(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setDate(final String parameterName, final Date x) throws SQLException {
		this.setDate(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setTime(final String parameterName, final Time x) throws SQLException {
		this.setTime(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setTimestamp(final String parameterName, final Timestamp x) throws SQLException {
		this.setTimestamp(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @param length the data length
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setAsciiStream(final String parameterName, final InputStream x, final int length) throws SQLException {
		this.setAsciiStream(this.getParameterIndex(parameterName), x, length);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @param length the data length
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setUnicodeStream(
			final String parameterName, final InputStream x, final int length) throws SQLException {
		this.setUnicodeStream(this.getParameterIndex(parameterName), x, length);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @param length the data length
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setBinaryStream(final String parameterName, final InputStream x, final int length) throws SQLException {
		this.setBinaryStream(this.getParameterIndex(parameterName), x, length);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @param targetSqlType the target SQL type
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setObject(final String parameterName, final Object x, final int targetSqlType) throws SQLException {
		this.setObject(this.getParameterIndex(parameterName), x, targetSqlType);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setObject(final String parameterName, final Object x) throws SQLException {
		this.setObject(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param reader the data reader
	 * @param length the data length
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setCharacterStream(
			final String parameterName, final Reader reader, final int length) throws SQLException {
		this.setCharacterStream(this.getParameterIndex(parameterName), reader, length);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setRef(final String parameterName, final Ref x) throws SQLException {
		this.setRef(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setBlob(final String parameterName, final Blob x) throws SQLException {
		this.setBlob(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setClob(final String parameterName, final Clob x) throws SQLException {
		this.setClob(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setArray(final String parameterName, final Array x) throws SQLException {
		this.setArray(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @param cal the date calendar
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setDate(final String parameterName, final Date x, final Calendar cal) throws SQLException {
		this.setDate(this.getParameterIndex(parameterName), x, cal);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @param cal the time calendar
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setTime(final String parameterName, final Time x, final Calendar cal) throws SQLException {
		this.setTime(this.getParameterIndex(parameterName), x, cal);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @param cal the time calendar
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setTimestamp(final String parameterName, final Timestamp x, final Calendar cal) throws SQLException {
		this.setTimestamp(this.getParameterIndex(parameterName), x, cal);
	}

	/**
	 * @param parameterName the parameter name
	 * @param sqlType the SQL type
	 * @param typeName the data type name
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setNull(final String parameterName, final int sqlType, final String typeName) throws SQLException {
		this.setNull(this.getParameterIndex(parameterName), sqlType, typeName);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setURL(final String parameterName, final URL x) throws SQLException {
		this.setURL(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setRowId(final String parameterName, final RowId x) throws SQLException {
		this.setRowId(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param value the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setNString(final String parameterName, final String value) throws SQLException {
		this.setNString(this.getParameterIndex(parameterName), value);
	}

	/**
	 * @param parameterName the parameter name
	 * @param value the parameter value
	 * @param length the data length
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setNCharacterStream(
			final String parameterName, final Reader value, final long length) throws SQLException {
		this.setNCharacterStream(this.getParameterIndex(parameterName), value, length);
	}

	/**
	 * @param parameterName the parameter name
	 * @param value the parameter value
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setNClob(final String parameterName, final NClob value) throws SQLException {
		this.setNClob(this.getParameterIndex(parameterName), value);
	}

	/**
	 * @param parameterName the parameter name
	 * @param reader the data reader
	 * @param length the data length
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setClob(final String parameterName, final Reader reader, final long length) throws SQLException {
		this.setClob(this.getParameterIndex(parameterName), reader, length);
	}

	/**
	 * @param parameterName the parameter name
	 * @param inputStream the data stream
	 * @param length the data length
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setBlob(
			final String parameterName, final InputStream inputStream, final long length) throws SQLException {
		this.setBlob(this.getParameterIndex(parameterName), inputStream, length);
	}

	/**
	 * @param parameterName the parameter name
	 * @param reader the data reader
	 * @param length the data length
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setNClob(final String parameterName, final Reader reader, final long length) throws SQLException {
		this.setNClob(this.getParameterIndex(parameterName), reader, length);
	}

	/**
	 * @param parameterName the parameter name
	 * @param xmlObject the XML data object
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setSQLXML(final String parameterName, final SQLXML xmlObject) throws SQLException {
		this.setSQLXML(this.getParameterIndex(parameterName), xmlObject);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the parameter value
	 * @param targetSqlType the target SQL type
	 * @param scaleOrLength the data scale or length
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setObject(
			final String parameterName, final Object x, final int targetSqlType, final int scaleOrLength
	) throws SQLException {
		this.setObject(this.getParameterIndex(parameterName), x, targetSqlType, scaleOrLength);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the data stream
	 * @param length the data length
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setAsciiStream(
			final String parameterName, final InputStream x, final long length) throws SQLException {
		this.setAsciiStream(this.getParameterIndex(parameterName), x, length);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the data stream
	 * @param length the data length
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setBinaryStream(
			final String parameterName, final InputStream x, final long length) throws SQLException {
		this.setBinaryStream(this.getParameterIndex(parameterName), x, length);
	}

	/**
	 * @param parameterName the parameter name
	 * @param reader the data reader
	 * @param length the data length
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setCharacterStream(
			final String parameterName, final Reader reader, final long length) throws SQLException {
		this.setCharacterStream(this.getParameterIndex(parameterName), reader, length);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the data stream
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setAsciiStream(final String parameterName, final InputStream x) throws SQLException {
		this.setAsciiStream(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param x the data stream
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setBinaryStream(final String parameterName, final InputStream x) throws SQLException {
		this.setBinaryStream(this.getParameterIndex(parameterName), x);
	}

	/**
	 * @param parameterName the parameter name
	 * @param reader the data reader
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setCharacterStream(final String parameterName, final Reader reader) throws SQLException {
		this.setCharacterStream(this.getParameterIndex(parameterName), reader);
	}

	/**
	 * @param parameterName the parameter name
	 * @param reader the data reader
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setNCharacterStream(final String parameterName, final Reader reader) throws SQLException {
		this.setNCharacterStream(this.getParameterIndex(parameterName), reader);
	}

	/**
	 * @param parameterName the parameter name
	 * @param reader the data reader
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setClob(final String parameterName, final Reader reader) throws SQLException {
		this.setClob(this.getParameterIndex(parameterName), reader);
	}

	/**
	 * @param parameterName the parameter name
	 * @param inputStream the data stream
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setBlob(final String parameterName, final InputStream inputStream) throws SQLException {
		this.setBlob(this.getParameterIndex(parameterName), inputStream);
	}

	/**
	 * @param parameterName the parameter name
	 * @param reader the data reader
	 * @throws SQLException if parameterName does not correspond to a parameter marker in the SQL statement
	 */
	public void setNClob(final String parameterName, final Reader reader) throws SQLException {
		this.setNClob(this.getParameterIndex(parameterName), reader);
	}
}
