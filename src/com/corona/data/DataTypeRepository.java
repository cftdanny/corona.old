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
public class DataTypeRepository {

	/**
	 * single instance for this resolvers
	 */
	private static DataTypeRepository instance = null;
	
	/**
	 * all predefined column value resolvers
	 */
	private Map<Class<?>, DataType> dataTypes = new HashMap<Class<?>, DataType>();
	
	/**
	 * do not allow to create this class and create all predefined resolvers
	 */
	protected DataTypeRepository() {
		
		// OBJECT
		this.dataTypes.put(Object.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getObject(column);
			}
		});

		// STRING
		this.dataTypes.put(String.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return trim(resultHolder.getString(column));
			}
			private String trim(final String str) {
				return str == null ? null : str.trim();
			}
		});

		// BYTE
		this.dataTypes.put(Byte.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getByte(column);
			}
		});
		this.dataTypes.put(byte.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getByte(column);
			}
		});

		// SHORT
		this.dataTypes.put(Short.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getShort(column);
			}
		});
		this.dataTypes.put(short.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getShort(column);
			}
		});

		// INTEGR
		this.dataTypes.put(Integer.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getInteger(column);
			}
		});
		this.dataTypes.put(int.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getInteger(column);
			}
		});

		// LONG
		this.dataTypes.put(Long.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getLong(column);
			}
		});
		this.dataTypes.put(long.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getLong(column);
			}
		});
		
		// FLOAT
		this.dataTypes.put(Float.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getFloat(column);
			}
		});
		this.dataTypes.put(float.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getFloat(column);
			}
		});

		// DOUBLE
		this.dataTypes.put(Double.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getDouble(column);
			}
		});
		this.dataTypes.put(double.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getDouble(column);
			}
		});

		// BOOLEAN
		this.dataTypes.put(Boolean.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getBoolean(column);
			}
		});
		this.dataTypes.put(boolean.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getBoolean(column);
			}
		});

		// DATE
		this.dataTypes.put(Date.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getDate(column);
			}
		});
		this.dataTypes.put(java.util.Date.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getDate(column);
			}
		});

		// CALENDAR
		this.dataTypes.put(Calendar.class, new DataType() {
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
		this.dataTypes.put(Timestamp.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getTimestamp(column);
			}
		});

		// TIME
		this.dataTypes.put(Time.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getTime(column);
			}
		});

		// URL
		this.dataTypes.put(URL.class, new DataType() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getURL(column);
			}
		});
	}
	
	/**
	 * @return single instance for this resolver
	 */
	public static DataTypeRepository getInstance() {
		
		if (instance == null) {
			instance = new DataTypeRepository();
		}
		return instance;
	}
	
	/**
	 * @param type the class of value
	 * @return the column value resolver
	 */
	public DataType find(final Class<?> type) {
		
		DataType dataType = this.dataTypes.get(type);
		if (dataType == null) {
			dataType = this.dataTypes.get(Object.class);
		}
		return dataType;
	}
}
