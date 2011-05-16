/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.hsqldb;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.corona.data.Command;
import com.corona.data.sql.SQLCommand;
import com.corona.data.sql.SQLDialect;
import com.corona.data.DataRuntimeException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>The dialect for MySQL Server. </p>
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
		return new Timestamp(new Date().getTime());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getGeneratedKeys(com.corona.data.Command)
	 */
	@Override
	public Object[] getGeneratedKeys(final Command command) {
		
		ResultSet resultset = null;
		try {
			
			List<Object> keys = new ArrayList<Object>();
			resultset = ((SQLCommand) command).getSource().getGeneratedKeys();
			if (resultset.next()) {
				keys.add(resultset.getObject(1));
			}
			return keys.toArray();
		} catch (Exception e) {
			
			this.logger.error("Fail to get generated key from last MySQL INSERT statement", e);
			throw new DataRuntimeException("Fail to get generated key from last MySQL INSERT statement", e);
		} finally {
			
			if (resultset != null) {
				try {
					resultset.close();
				} catch (Exception e) {
					
					this.logger.error("Fail to close the opened MySQL result set", e);
					throw new DataRuntimeException("Fail to close the opened MySQL result set", e);
				}
			}
		}
	}
}
