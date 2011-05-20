/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

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
				return resultHolder.get(column);
			}
		});

		// STRING
		this.resolvers.put(String.class, new ColumnValueResolver() {
			public Object get(final ResultHolder resultHolder, final String column) {
				return resultHolder.getString(column);
			}
		});

		// INTEGR
		this.resolvers.put(Integer.class, new ColumnValueResolver() {
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
