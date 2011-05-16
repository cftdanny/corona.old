/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>This handler is used to transfer query result from data source into instance or list of map to entity </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public interface ResultHandler<E> {

	/**
	 * the result handler that transfer query result to list of LONG
	 */
	ResultHandler<Long> LONG = new AbstractResultHandler<Long>() {
		
		public Long get(final ResultHolder result) {
			return (Long) result.get(1);
		}
	};

	/**
	 * the result handler that transfer query result to list of LONG
	 */
	ResultHandler<String> STRING = new AbstractResultHandler<String>() {
		
		public String get(final ResultHolder result) {
			Object outcome = result.get(1);
			return (outcome == null) ? null : outcome.toString();
		}
	};

	/**
	 * the result handler that transfer query result to list of LONG
	 */
	ResultHandler<Integer> INTEGER = new AbstractResultHandler<Integer>() {
		
		public Integer get(final ResultHolder result) {
			return (Integer) result.get(1);
		}
	};

	/**
	 * the result handler that transfer query result to list of LONG
	 */
	ResultHandler<Object> OBJECT = new AbstractResultHandler<Object>() {
		
		public Object get(final ResultHolder result) {
			return result.get(1);
		}
	};

	/**
	 * the result handler that transfer query result to list of LONG
	 */
	ResultHandler<List<Object>> LIST = new AbstractResultHandler<List<Object>>() {
		
		public List<Object> get(final ResultHolder result) {
			
			List<Object> outcome = new ArrayList<Object>();
			for (int i = 1; i <= result.getColumnCount(); i++) {
				outcome.add(result.get(i));
			}
			return outcome;
		}
	};

	/**
	 * the result handler that transfer query result to list of LONG
	 */
	ResultHandler<Object[]> ARRAY = new AbstractResultHandler<Object[]>() {
		
		public Object[] get(final ResultHolder result) {
			
			Object[] outcome = new Object[result.getColumnCount()];
			for (int i = 1; i <= result.getColumnCount(); i++) {
				outcome[i - 1] = result.get(i);
			}
			return outcome;
		}
	};

	/**
	 * the result handler that transfer query result to list of LONG
	 */
	ResultHandler<Map<String, Object>> MAP = new AbstractResultHandler<Map<String, Object>>() {
		
		public Map<String, Object> get(final ResultHolder result) {
			
			Map<String, Object> outcome = new HashMap<String, Object>();
			for (String label : result.getColumnLabels()) {
				outcome.put(label, result.get(label));
			}
			return outcome;
		}
	};

	/**
	 * <p>Transfer current row in query result to instance of entity class. </p>
	 *  
	 * @param result the query result from data source
	 * @return the instance of entity that is transfered from query result
	 */
	E get(ResultHolder result);
	
	/**
	 * <p>Transfer all rows in query result to list of entity class. </p>
	 * 
	 * @param result the query result from data source
	 * @return the list of entity that is transfered from query result
	 */
	List<E> toList(ResultHolder result);
}
