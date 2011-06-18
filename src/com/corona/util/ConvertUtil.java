/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>This utility class is used to convert data from one type to another </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class ConvertUtil {

	/**
	 * all converters
	 */
	private static Map<Type, Map<Type, Converter>> converters = null;
	
	/**
	 * register all predefined converters
	 */
	static {
		converters = new HashMap<Type, Map<Type, Converter>>();
		
		// STRING
		register(String.class, String.class, new Converter() {
			public Object convert(final Object value) {
				return value;
			}
		});

		// INTEGER
		register(String.class, Integer.class, new Converter() {
			public Object convert(final Object value) {
				return new Integer((String) value);
			}
		});
		register(String.class, int.class, new Converter() {
			public Object convert(final Object value) {
				return Integer.parseInt((String) value);
			}
		});

		// LONG
		register(String.class, Long.class, new Converter() {
			public Object convert(final Object value) {
				return new Long((String) value);
			}
		});
		register(String.class, long.class, new Converter() {
			public Object convert(final Object value) {
				return Long.parseLong((String) value);
			}
		});

		// FLOAT
		register(String.class, Float.class, new Converter() {
			public Object convert(final Object value) {
				return new Float((String) value);
			}
		});
		register(String.class, float.class, new Converter() {
			public Object convert(final Object value) {
				return Float.parseFloat((String) value);
			}
		});

		// DOUBLE
		register(String.class, Double.class, new Converter() {
			public Object convert(final Object value) {
				return new Double((String) value);
			}
		});
		register(String.class, double.class, new Converter() {
			public Object convert(final Object value) {
				return Double.parseDouble((String) value);
			}
		});

		// BOOLEAN
		register(String.class, Boolean.class, new Converter() {
			public Object convert(final Object value) {
				return new Boolean((String) value);
			}
		});
		register(String.class, boolean.class, new Converter() {
			public Object convert(final Object value) {
				return Boolean.parseBoolean((String) value);
			}
		});

		// SHORT
		register(String.class, Short.class, new Converter() {
			public Object convert(final Object value) {
				return new Short((String) value);
			}
		});
		register(String.class, short.class, new Converter() {
			public Object convert(final Object value) {
				return Short.parseShort((String) value);
			}
		});

		// BYTE
		register(String.class, Byte.class, new Converter() {
			public Object convert(final Object value) {
				return new Byte((String) value);
			}
		});
		register(String.class, byte.class, new Converter() {
			public Object convert(final Object value) {
				return Byte.parseByte((String) value);
			}
		});
	}
	
	/**
	 * utility class
	 */
	private ConvertUtil() {
		// do nothing
	}
	
	/**
	 * @param sourceType the source data type
	 * @param targetType the convert to data type
	 * @param converter the converter
	 */
	public static void register(final Type sourceType, final Type targetType, final Converter converter) {
		
		Map<Type, Converter> sourceConverters = converters.get(sourceType);
		if (sourceConverters == null) {
			sourceConverters = new HashMap<Type, Converter>();
			converters.put(sourceType, sourceConverters);
		}
		sourceConverters.put(targetType, converter);
	}

	/**
	 * @param sourceType the source data type
	 * @param targetType the convert to data type
	 * @return whether converter is registered
	 */
	public static boolean canConvert(final Type sourceType, final Type targetType) {
		
		Map<Type, Converter> sourceConverters = converters.get(sourceType);
		return (sourceConverters == null) ? false : sourceConverters.containsKey(targetType);
	}
	
	/**
	 * @param targetType the target type
	 * @return whether converter is register that from string to target type 
	 */
	public static boolean canConvertFromString(final Type targetType) {
		return canConvert(String.class, targetType);
	}
	
	/**
	 * @param sourceType the source data type
	 * @param targetType the convert to data type
	 * @return converter the converter
	 */
	public static Converter get(final Type sourceType, final Type targetType) {
		
		Map<Type, Converter> sourceConverters = converters.get(sourceType);
		return (sourceConverters == null) ? null : sourceConverters.get(targetType);
	}

	/**
	 * @param value the source value
	 * @param source the source type
	 * @param target convert to type
	 * @return convert to value
	 */
	public static Object getAsType(final Object value, final Type source, final Type target) {
		return (value != null) ? get(source, target).convert(value) : null;
	}

	/**
	 * @param value the source value
	 * @param type convert to type
	 * @return convert to value
	 */
	public static Object getAsType(final Object value, final Type type) {
		return (value != null) ? getAsType(value.getClass(), type) : null;
	}

	/**
	 * @param value the source value
	 * @param type convert to type
	 * @return convert to value
	 */
	public static Object getAsType(final String value, final Type type) {
		return getAsType(value, String.class, type);
	}

	/**
	 * @param values the parameter value
	 * @param source the source type
	 * @param target the target type
	 * @return the list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getAsList(final Object[] values, final Type source, final Type target) {
		
		if (values == null) {
			return null;
		} 
		
		Converter converter = get(source, target);
		if (converter == null) {
			throw new IllegalArgumentException(
					"Converter from " + source.toString() + " to " + target.toString() + " is not defined"
			);
		}
		
		List result = new ArrayList();
		for (Object value : values) {
			result.add(converter.convert(value));
		}
		return result;
	}

	/**
	 * @param values the parameter value
	 * @param type convert to type
	 * @return the list
	 */
	@SuppressWarnings("rawtypes")
	public static List getAsList(final String[] values, final Type type) {
		return getAsList(values, String.class, type);
	}
}
