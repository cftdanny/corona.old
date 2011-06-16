/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.param;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>The utility class in order to get values from request parameters. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class ParamUtil {

	/**
	 * all supported simple types that String can convert to
	 */
	private static Map<Type, ParamValueConverter> simpleTypes = null;
	
	/**
	 * initial simple type converter for all supported simple types
	 */
	static {
		simpleTypes = new HashMap<Type, ParamValueConverter>();
		
		// STRING
		simpleTypes.put(String.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return value;
			}
		});

		// INTEGER
		simpleTypes.put(Integer.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return new Integer(value);
			}
		});
		simpleTypes.put(int.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return Integer.parseInt(value);
			}
		});

		// LONG
		simpleTypes.put(Long.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return new Long(value);
			}
		});
		simpleTypes.put(long.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return Long.parseLong(value);
			}
		});

		// FLOAT
		simpleTypes.put(Float.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return new Float(value);
			}
		});
		simpleTypes.put(float.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return Float.parseFloat(value);
			}
		});

		// DOUBLE
		simpleTypes.put(Double.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return new Double(value);
			}
		});
		simpleTypes.put(double.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return Double.parseDouble(value);
			}
		});

		// BOOLEAN
		simpleTypes.put(Boolean.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return new Boolean(value);
			}
		});
		simpleTypes.put(boolean.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return Boolean.parseBoolean(value);
			}
		});

		// SHORT
		simpleTypes.put(Short.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return new Short(value);
			}
		});
		simpleTypes.put(short.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return Short.parseShort(value);
			}
		});

		// BYTE
		simpleTypes.put(Byte.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return new Byte(value);
			}
		});
		simpleTypes.put(byte.class, new ParamValueConverter() {
			public Object convert(final String value) {
				return Byte.parseByte(value);
			}
		});
	}
	
	/**
	 * utility class
	 */
	private ParamUtil() {
		// do nothing
	}

	/**
	 * @param type the class type to check
	 * @return whether argument type is simple type
	 */
	public static boolean isSimpleType(final Type type) {
		return simpleTypes.containsKey(type);
	}

	/**
	 * @param value the string
	 * @param type the type class that converts string to
	 * @return the converted value
	 */
	public static Object getAsType(final String value, final Type type) {
		
		if (value != null) {
			ParamValueConverter converter = simpleTypes.get(type);
			if (converter != null) {
				return converter.convert(value);
			} else {
				throw new IllegalArgumentException("Can not convert String to " + type.toString());
			}
		} else {
			return null;
		}
	}
	
	/**
	 * @param values the parameter values
	 * @return the list of String
	 */
	public static List<String> getAsList(final String[] values) {
		
		if (values != null) {
			List<String> result = new ArrayList<String>();
			for (String value : values) {
				result.add(value);
			}
			return result;
		} else {
			return null;
		}
	}
	
	/**
	 * @param values the parameter value
	 * @param type convert to type
	 * @return the list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getAsList(final String[] values, final Type type) {
		
		if (values != null) {
			
			ParamValueConverter converter = simpleTypes.get(type);
			if (converter != null) {
				List result = new ArrayList();
				for (String value : values) {
					result.add(converter.convert(value));
				}
				return result;
			} else {
				throw new IllegalArgumentException("Can not convert String to " + type.toString());
			}
		} else {
			return null;
		}
	}
}
