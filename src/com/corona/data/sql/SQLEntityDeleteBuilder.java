/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.regex.Pattern;

import com.corona.data.EntityDeleteBuilder;
import com.corona.data.EntityMetaData;

/**
 * <p>This builder is used to build DELETE SQL statement by entity configuration and filter </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLEntityDeleteBuilder implements EntityDeleteBuilder {

	/**
	 * the pattern to match whether SQL statement starts with WHERE
	 */
	private static final Pattern PATTERN = Pattern.compile("(\\s*[w|W][h|H][e|E][r|R][e|E]\\s+).*");

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityDeleteBuilder#build(com.corona.data.EntityMetaData, java.lang.String)
	 */
	@Override
	public String build(final EntityMetaData<?> config, final String filter) {
		
		if (PATTERN.matcher(filter).matches()) {
			return "DELETE FROM " + config.getName() + " " + filter;
		} else {
			return "DELETE FROM " + config.getName() + " WHERE " + filter;
		}
	}
}
