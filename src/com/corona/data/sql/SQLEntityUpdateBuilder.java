/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.regex.Pattern;

import com.corona.data.EntityMetaData;
import com.corona.data.EntityUpdateBuilder;

/**
 * <p>This builder is used to build UPDATE SQL statement by entity configuration and filter </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLEntityUpdateBuilder implements EntityUpdateBuilder {

	/**
	 * the pattern to match whether SQL statement starts with WHERE
	 */
	private static final Pattern PATTERN = Pattern.compile("(\\s*[s|S][e|E][t|T]\\s+).*");

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityUpdateBuilder#build(com.corona.data.EntityMetaData, java.lang.String)
	 */
	@Override
	public String build(final EntityMetaData<?> config, final String filter) {
		
		if (PATTERN.matcher(filter).matches()) {
			return "UPDATE " + config.getName() + " " + filter;
		} else {
			return "UPDATE " + config.getName() + " SET " + filter;
		}
	}
}
