/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.util;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * <p>A helper utility to manage String. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class StringUtil {

	/**
	 * utility class
	 */
	protected StringUtil() {
		// do nothing
	}
	
	/**
	 * @param str the string
	 * @return <code>true</code> if string is blank (null, "", or can be trimmed to "")
	 */
	public static boolean isBlank(final String str) {
		return StringUtil.isEmpty(str) || (str.trim().length() == 0);
	}
	
	/**
	 * @param str the string
	 * @return <code>true</code> if string is empty (null, or "")
	 */
	public static boolean isEmpty(final String str) {
		return (str == null) || (str.length() == 0);
	}
	
	/**
	 * @param separator the separator to join array of string
	 * @param array the array of strings to join
	 * @return the joined string
	 */
	public static String join(final String separator, final String[] array) {
		
		String result = null;
		for (String str : array) {
			result = (result == null ? "" : result + separator) + str;
		}
		return result;
	}

	/**
	 * @param separator the separator to join array of string
	 * @param before the string will insert before every item in array
	 * @param after the string will append after every item in array
	 * @param array the array of strings to join
	 * @return the joined string
	 */
	public static String join(final String separator, final String before, final String after, final String[] array) {
		
		if ((array != null) && (array.length > 0)) {
			String result = null;
			for (String str : array) {
				result = (result == null ? "" : result + separator) + before + str + after;
			}
			return result;
		} else {
			return "";
		}
	}
	
	/**
	 * @param separator the separator to join array of string
	 * @param before the string will insert before every item in array
	 * @param after the string will append after every item in array
	 * @param collection collection of strings to join
	 * @return the joined string
	 */
	public static String join(
			final String separator, final String before, final String after, final Collection<String> collection
	) {
		
		if ((collection != null) && (collection.size() > 0)) {
			String result = null;
			for (String str : collection) {
				result = (result == null ? "" : result + separator) + before + str + after;
			}
			return result;
		} else {
			return "";
		}
	}

	/**
	 * <p>Convert a string to camel style. Usually, it is getter and setter in java variable </p>
	 *
	 * @param value The string
	 * @return The camel string
	 */
	public static String camel(final String value) {
		if (!isBlank(value)) {
			return value.substring(0, 1).toLowerCase() + value.substring(1);
		} else {
			return value;
		}
	}
	
	/**
	 * @param str the string to be trimmed, can be <code>null</code>
	 * @return the trimmed string, and if str is <code>null</code>, will return ""
	 */
	public static String trim(final String str) {
		return str == null ? "" : str.trim();
	}

	/**
	 * @param str the string to be stripped, be be <code>null</code>
	 * @return the stripped string, and if str is <code>null</code>, will return ""
	 */
	public static String strip(final String str) {
		
		if (str != null) {
			int i = 0;
			for (int count = str.length(); i < count; i++) {
				if (str.charAt(i) == ' ') {
					i++;
				} else {
					break;
				}
			}
			
			return i == 0 ? str : str.substring(i).trim();
		} else {
			return "";
		}
	}

	/**
	 * @param str the string
	 * @param type the type class that converts string to
	 * @return the converted value
	 */
	public static Object to(final String str, final Type type) {
		
		if (str != null) {
			
			// TO STRING
			if (String.class.equals(type)) {
				return str;
			} 
			
			// TO INTEGER
			if (Integer.class.equals(type)) {
				return new Integer(str);
			} 
			if (int.class.equals(type)) {
				return Integer.parseInt(str);
			} 
			
			// TO LONG
			if (Long.class.equals(type)) {
				return new Long(str);
			} 
			if (long.class.equals(type)) {
				return Long.parseLong(str);
			} 
			
			// TO FLOAT
			if (Float.class.equals(type)) {
				return new Float(str);
			} 
			if (float.class.equals(type)) {
				return Float.parseFloat(str);
			}
			
			// TO DOUBLE
			if (Double.class.equals(type)) {
				return new Double(str);
			} 
			if (double.class.equals(type)) {
				return Double.parseDouble(str);
			} 
			
			// TO BOOLEAN
			if (Boolean.class.equals(type)) {
				return new Boolean(str);
			} 
			if (boolean.class.equals(type)) {
				return Boolean.parseBoolean(str);
			} 
			
			// TO SHORT
			if (Short.class.equals(type)) {
				return new Short(str);
			} 
			if (short.class.equals(type)) {
				return Short.parseShort(str);
			} 
			
			// TO BYTE
			if (Byte.class.equals(type)) {
				return new Byte(str);
			} 
			if (byte.class.equals(type)) {
				return Byte.parseByte(str);
			} 
			
			throw new IllegalArgumentException("Can not convert String to [" + type.toString() + "]"); 
		} else {
			return null;
		}
	}
}
