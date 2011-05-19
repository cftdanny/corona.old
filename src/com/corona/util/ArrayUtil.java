/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.util;

/**
 * <p>This helper class is used to manage array. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class ArrayUtil {

	/**
	 * utility class
	 */
	protected ArrayUtil() {
		// do nothing
	}
	
	/**
	 * @param values the values to join
	 * @param delimiter the delimiter for joined string
	 * @return the joined string
	 */
	public static String join(final String[] values, final String delimiter) {
		
		String result = "";
		for (String value : values) {
			result = result + ((result.length() == 0) ? "" : delimiter) + value;
		}
		return result;
	}
}
