/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * <p>This class is used to replace named parameter SQL statement to standard JDBC SQL statement. For example, 
 * replace :name into ?. And also provide methods to set prepared statement parameters by numbered parameters 
 * or named parameters. </p>
 * 
 * @author $Author$
 * @version $Id$
 */
public class SQLParser extends HashMap<String, Integer> {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2080488876142175172L;

	/**
	 * the SQL parameter name pattern
	 */
	private static final Pattern PATTERN = Pattern.compile("\\w");
	
	/**
	 * the JDBC standard SQL statement 
	 */
	private String clause;
	
	/**
	 * @param sql the named SQL statement
	 * @throws SQLException if fail to prepare named SQL statement
	 */
	public SQLParser(final String sql) throws SQLException {
		
		// the machine state to parse named SQL statement
		int state = 0;
		
		// the index and name of named parameter 
		int index = 0;
		
		// the string builder to keep parsed parameter name 
		StringBuilder name = new StringBuilder();
		
		// the final numbered parameter SQL statement
		StringBuilder result = new StringBuilder(512);
		for (int i = 0, count = sql.length(); i < count; i++) {
			
			char c = sql.charAt(i);
			switch (state) {
				case 0:
					if (c == ':') {
						index = index + 1;
						name = new StringBuilder(10);
						
						state = 1;
					} else if (c == '\'') {
						result.append(c);
						
						state = 10;
					} else if (c == '"') {
						result.append(c);
						
						state = 20;
					} else {
						result.append(c);
					}
					break;
				
				case 1:
					if (!PATTERN.matcher(String.valueOf(c)).matches()) {
						this.put(name.toString(), index);
						
						result.append('?').append(c);
						
						state = 0;
					} else {
						name.append(c);
					}
					break;
				
				case 10:
					if (c == '\\') {
						state = 11;
					}
					result.append(c);
					break;
					
				case 11:
					state = 10;
					break;
					
				case 20:
					if (c == '\'') {
						state = 21;
					}
					result.append(c);
					break;
					
				case 21:
					state = 20;
					result.append(c);
					
				default:
					break;
			}
		}
		
		if (state == 1) {
			this.put(name.toString(), index);
			result.append('?');
		}
		
		this.clause = result.toString();
	}

	/**
	 * @return the JDBC standard SQL statement 
	 */
	public String getClause() {
		return clause;
	}
}