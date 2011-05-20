/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.regex.Pattern;

import com.corona.data.EntityMetaData;
import com.corona.util.StringUtil;

/**
 * <p>This builder is used to build SQL SELECT statement with entity class and filter. </p>

 * <p>For example, the following SELECT SQL statements does not have a joint table: </p>
 * <pre>
 * 	WHERE ABC = 1
 * 	ABC = 1 AND CDE = 2
 * </pre>
 * 
 * <p>For example, the following SELECT SQL statements has join tables: </p>
 * <pre>
 * 	, SECOND S WHERE A = 1 AND S.B = 2
 * 	A, B B, C C WHERE A.A = B.A AND B.A = C.A
 * </pre>
 * 
 *
 * @author $Author$
 * @version $Id$
 */
class SQLSelectBuilder {

	/**
	 * the pattern to match whether SQL statement starts with WHERE
	 */
	private static final Pattern WHERE = Pattern.compile(
			"(\\s*[w|W][h|H][e|E][r|R][e|E]\\s+).*"
	);
	
	/**
	 * the pattern to match ORDER BY SQL statement 
	 */
	private static final Pattern ORDERBY = Pattern.compile(
			"(\\s*[o|O][r|R][d|D][e|E][r|R]\\s+[b|B][y|Y]\\s+).*"
	);
	
	/**
	 * the pattern to match whether SQL statement has join table 
	 */
	private static final Pattern JOINS = Pattern.compile(
			"\\s*(\\w*)\\s*,.*"
	);
	
	/**
	 * the FROM SQL statement
	 */
	private String filter = "";
	
	/**
	 * @param config the entity configuration
	 * @param filter the filter SQL statement for WHERE or ORDER BY
	 */
	SQLSelectBuilder(final EntityMetaData<?> config, final String filter) {
		
		this.filter = (filter == null) ? "" : filter.trim();
		
		boolean matched = WHERE.matcher(this.filter).matches();
		if (!matched) {
			matched = JOINS.matcher(this.filter).matches();
		}
		if (!matched) {
			matched = ORDERBY.matcher(this.filter).matches();
		}

		if (!StringUtil.isBlank(this.filter)) {
			if (!matched) {
				this.filter = "WHERE " + this.filter;
			}
			this.filter = " " + this.filter;
		}
		this.filter = " FROM " + config.getName() + this.filter;
	}
	
	/**
	 * @return the SELECT SQL statement
	 */
	String getStatement() {
		return this.getStatement("SELECT * ");
	}

	/**
	 * @param sql the SQL statement before FROM clause 
	 * @return the SQL statement
	 */
	String getStatement(final String sql) {
		return sql + this.filter;
	}
}

