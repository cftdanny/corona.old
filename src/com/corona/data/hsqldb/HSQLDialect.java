/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.hsqldb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.corona.data.Command;
import com.corona.data.sql.SQLDialect;
import com.corona.data.DataRuntimeException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>The dialect for HSQLDB. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class HSQLDialect extends SQLDialect {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(HSQLDialect.class);
	
	/**
	 * the MySQL JDBC connection manager
	 */
	private HSQLConnectionManager connectionManager;
	
	/**
	 * @param connectionManager the connection manager
	 */
	HSQLDialect(final HSQLConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getCurrentTime()
	 */
	@Override
	public Timestamp getCurrentTime() {

		Statement statement = null;
		ResultSet resultSet = null;

		// try to get current time from HSQLDB database server
		try {
			statement = this.connectionManager.getSource().createStatement();
			resultSet = statement.executeQuery("VALUES (CURRENT_TIMESTAMP)"); 
			return resultSet.next() ? resultSet.getTimestamp(1) : null;
		} catch (SQLException e) {
			
			this.logger.error("Fail to get current time from MySQL", e);
			throw new DataRuntimeException("Fail to get current time from MySQL", e);
		} finally {
			
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
					this.logger.error("Fail to close MySQL statement", e);
					throw new DataRuntimeException("Fail to close MySQL statement", e);
				}
			}
			
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					this.logger.error("Fail to close MySQL queried result set", e);
					throw new DataRuntimeException("Fail to close MySQL queried result set", e);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getGeneratedKeys(com.corona.data.Command)
	 */
	@Override
	public Object[] getGeneratedKeys(final Command command) {
		
		Statement statement = null;
		ResultSet resultset = null;
		try {
			
			statement = this.connectionManager.getSource().createStatement();
			resultset = statement.executeQuery("CALL IDENTITY()");
			
			List<Object> keys = new ArrayList<Object>();
			if (resultset.next()) {
				keys.add(resultset.getObject(1));
			}
			return keys.toArray();
		} catch (Exception e) {
			
			this.logger.error("Fail to get generated key from last HSQLDB INSERT statement", e);
			throw new DataRuntimeException("Fail to get generated key from last HSQLDB INSERT statement", e);
		} finally {
			
			if (resultset != null) {
				try {
					resultset.close();
				} catch (Exception e) {
					
					this.logger.error("Fail to close the opened HSQLDB result set", e);
					throw new DataRuntimeException("Fail to close the opened HSQLDB result set", e);
				}
			}
			
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					this.logger.error("Fail to close the opened MySQL result set", e);
					throw new DataRuntimeException("Fail to close the opened MySQL result set", e);
				}
			}
		}
	}
}
