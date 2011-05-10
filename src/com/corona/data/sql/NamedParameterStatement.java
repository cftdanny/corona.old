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
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSetMetaData;
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
public class NamedParameterStatement extends PreparedStatementWrapper {

	/**
	 * the named parameterized SQL statement parser
	 */
	private SQLParser parser;
	
	/**
	 * @param connection the opened JDBC connection
	 * @param sql the SQL statement
	 * @exception SQLException if fail to prepare SQL statement
	 */
	public NamedParameterStatement(final Connection connection, final String sql) throws SQLException {
		
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
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setBytes(int, byte[])
	 */
	@Override
	public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
		this.statement.setBytes(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setDate(int, java.sql.Date)
	 */
	@Override
	public void setDate(final int parameterIndex, final Date x) throws SQLException {
		this.statement.setDate(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setTime(int, java.sql.Time)
	 */
	@Override
	public void setTime(final int parameterIndex, final Time x) throws SQLException {
		this.statement.setTime(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setTimestamp(int, java.sql.Timestamp)
	 */
	@Override
	public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
		this.statement.setTimestamp(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream, int)
	 */
	@Override
	public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
		this.statement.setAsciiStream(parameterIndex, x, length);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setUnicodeStream(int, java.io.InputStream, int)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
		this.statement.setUnicodeStream(parameterIndex, x, length);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream, int)
	 */
	@Override
	public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
		this.statement.setBinaryStream(parameterIndex, x, length);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setObject(int, java.lang.Object, int)
	 */
	@Override
	public void setObject(final int parameterIndex, final Object x, final int targetSqlType) throws SQLException {
		this.statement.setObject(parameterIndex, x, targetSqlType);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setObject(int, java.lang.Object)
	 */
	@Override
	public void setObject(final int parameterIndex, final Object x) throws SQLException {
		this.statement.setObject(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader, int)
	 */
	@Override
	public void setCharacterStream(
			final int parameterIndex, final Reader reader, final int length) throws SQLException {
		this.statement.setCharacterStream(parameterIndex, reader, length);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setRef(int, java.sql.Ref)
	 */
	@Override
	public void setRef(final int parameterIndex, final Ref x) throws SQLException {
		this.statement.setRef(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setBlob(int, java.sql.Blob)
	 */
	@Override
	public void setBlob(final int parameterIndex, final Blob x) throws SQLException {
		this.statement.setBlob(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setClob(int, java.sql.Clob)
	 */
	@Override
	public void setClob(final int parameterIndex, final Clob x) throws SQLException {
		this.statement.setClob(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setArray(int, java.sql.Array)
	 */
	@Override
	public void setArray(final int parameterIndex, final Array x) throws SQLException {
		this.statement.setArray(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setDate(int, java.sql.Date, java.util.Calendar)
	 */
	@Override
	public void setDate(final int parameterIndex, final Date x, final Calendar cal) throws SQLException {
		this.statement.setDate(parameterIndex, x, cal);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setTime(int, java.sql.Time, java.util.Calendar)
	 */
	@Override
	public void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException {
		this.statement.setTime(parameterIndex, x, cal);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setTimestamp(int, java.sql.Timestamp, java.util.Calendar)
	 */
	@Override
	public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException {
		this.statement.setTimestamp(parameterIndex, x, cal);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setNull(int, int, java.lang.String)
	 */
	@Override
	public void setNull(final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
		this.statement.setNull(parameterIndex, sqlType, typeName);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setURL(int, java.net.URL)
	 */
	@Override
	public void setURL(final int parameterIndex, final URL x) throws SQLException {
		this.statement.setURL(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setRowId(int, java.sql.RowId)
	 */
	@Override
	public void setRowId(final int parameterIndex, final RowId x) throws SQLException {
		this.statement.setRowId(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setNString(int, java.lang.String)
	 */
	@Override
	public void setNString(final int parameterIndex, final String value) throws SQLException {
		this.statement.setNString(parameterIndex, value);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader, long)
	 */
	@Override
	public void setNCharacterStream(
			final int parameterIndex, final Reader value, final long length) throws SQLException {
		this.statement.setNCharacterStream(parameterIndex, value, length);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setNClob(int, java.sql.NClob)
	 */
	@Override
	public void setNClob(final int parameterIndex, final NClob value) throws SQLException {
		this.statement.setNClob(parameterIndex, value);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setClob(int, java.io.Reader, long)
	 */
	@Override
	public void setClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
		this.statement.setClob(parameterIndex, reader, length);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream, long)
	 */
	@Override
	public void setBlob(
			final int parameterIndex, final InputStream inputStream, final long length) throws SQLException {
		this.statement.setBlob(parameterIndex, inputStream, length);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader, long)
	 */
	@Override
	public void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
		this.statement.setNClob(parameterIndex, reader, length);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setSQLXML(int, java.sql.SQLXML)
	 */
	@Override
	public void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException {
		this.statement.setSQLXML(parameterIndex, xmlObject);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setObject(int, java.lang.Object, int, int)
	 */
	@Override
	public void setObject(
			final int parameterIndex, final Object x, final int targetSqlType, final int scaleOrLength
	) throws SQLException {
		this.statement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream, long)
	 */
	@Override
	public void setAsciiStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
		this.statement.setAsciiStream(parameterIndex, x, length);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream, long)
	 */
	@Override
	public void setBinaryStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
		this.statement.setBinaryStream(parameterIndex, x, length);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader, long)
	 */
	@Override
	public void setCharacterStream(
			final int parameterIndex, final Reader reader, final long length) throws SQLException {
		this.statement.setCharacterStream(parameterIndex, reader, length);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream)
	 */
	@Override
	public void setAsciiStream(final int parameterIndex, final InputStream x) throws SQLException {
		this.statement.setAsciiStream(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream)
	 */
	@Override
	public void setBinaryStream(final int parameterIndex, final InputStream x) throws SQLException {
		this.statement.setBinaryStream(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader)
	 */
	@Override
	public void setCharacterStream(final int parameterIndex, final Reader reader) throws SQLException {
		this.statement.setCharacterStream(parameterIndex, reader);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader)
	 */
	@Override
	public void setNCharacterStream(final int parameterIndex, final Reader value) throws SQLException {
		this.statement.setNCharacterStream(parameterIndex, value);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setClob(int, java.io.Reader)
	 */
	@Override
	public void setClob(final int parameterIndex, final Reader reader) throws SQLException {
		this.statement.setClob(parameterIndex, reader);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream)
	 */
	@Override
	public void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException {
		this.statement.setBlob(parameterIndex, inputStream);
	}

	/**
	 * {@inheritDoc}
	 * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader)
	 */
	@Override
	public void setNClob(final int parameterIndex, final Reader reader) throws SQLException {
		this.statement.setNClob(parameterIndex, reader);
	}
}
