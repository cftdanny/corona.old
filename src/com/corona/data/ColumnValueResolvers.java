/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>This class is used to store all predefined column value resolvers. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ColumnValueResolvers {

	/**
	 * single instance for this resolvers
	 */
	private static ColumnValueResolvers instance = null;
	
	/**
	 * all predefined column value resolvers
	 */
	private Map<Class<?>, ColumnValueResolver> resolvers = new HashMap<Class<?>, ColumnValueResolver>();
	
	/**
	 * do not allow to create this class and create all predefined resolvers
	 */
	protected ColumnValueResolvers() {
		
		// OBJECT
		this.resolvers.put(Object.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getObject(column);
			}
		});

		// STRING
		this.resolvers.put(String.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return trim(resultHolder.getString(column));
			}
			private String trim(final String str) {
				return str == null ? null : str.trim();
			}
		});

		// BYTE
		this.resolvers.put(Byte.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getByte(column);
			}
		});
		this.resolvers.put(byte.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getByte(column);
			}
		});

		// SHORT
		this.resolvers.put(Short.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getShort(column);
			}
		});
		this.resolvers.put(short.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getShort(column);
			}
		});

		// INTEGR
		this.resolvers.put(Integer.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getInteger(column);
			}
		});
		this.resolvers.put(int.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getInteger(column);
			}
		});

		// LONG
		this.resolvers.put(Long.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getLong(column);
			}
		});
		this.resolvers.put(long.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getLong(column);
			}
		});
		
		// FLOAT
		this.resolvers.put(Float.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getFloat(column);
			}
		});
		this.resolvers.put(float.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getFloat(column);
			}
		});

		// DOUBLE
		this.resolvers.put(Double.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getDouble(column);
			}
		});
		this.resolvers.put(double.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getDouble(column);
			}
		});

		// BOOLEAN
		this.resolvers.put(Boolean.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getBoolean(column);
			}
		});
		this.resolvers.put(boolean.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getBoolean(column);
			}
		});

		// DATE
		this.resolvers.put(Date.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getDate(column);
			}
		});
		this.resolvers.put(java.util.Date.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getDate(column);
			}
		});

		// CALENDAR
		this.resolvers.put(Calendar.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				
				Date date = resultHolder.getDate(column);
				if (date != null) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					return calendar;
				} else {
					return null;
				}
			}
		});
		
		// TIMESTAMP
		this.resolvers.put(Timestamp.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getTimestamp(column);
			}
		});

		// TIME
		this.resolvers.put(Time.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getTime(column);
			}
		});

		// URL
		this.resolvers.put(URL.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getURL(column);
			}
		});
	}
	
	/**
	 * @return single instance for this resolver
	 */
	public static ColumnValueResolvers getInstance() {
		
		if (instance == null) {
			instance = new ColumnValueResolvers();
		}
		return instance;
	}
	
	/**
	 * @param type the class of value
	 * @return the column value resolver
	 */
	public ColumnValueResolver find(final Class<?> type) {
		
		ColumnValueResolver resolver = this.resolvers.get(type);
		if (resolver == null) {
			resolver = this.resolvers.get(Object.class);
		}
		return resolver;
	}
}
