/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.util;

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
}
