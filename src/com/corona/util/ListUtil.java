/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.util;

import java.util.Arrays;
import java.util.List;

/**
 * <p>The utility class for list. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class ListUtil {

	/**
	 * utility class
	 */
	private ListUtil() {
		// do nothing
	}

	/**
	 * @param <T> the type
	 * @param values the values as array
	 * @return the values as list
	 */
	public static <T> List<T> getAsList(final T[] values) {
		return values == null ? null : Arrays.asList(values);
	}
}
