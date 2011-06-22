/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>This utility is used to open resource as input stream </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class ResourceUtil {

	/**
	 * utility class
	 */
	private ResourceUtil() {
		// do nothing
	}
	
	/**
	 * @param type the class to load resource
	 * @param resource the resource name
	 * @return the opened input stream
	 */
	public static String load(final Class<?> type, final String resource) {
		
		InputStream in = type.getResourceAsStream(resource);
		
		StringBuilder builder = new StringBuilder();
		try {
			int c = in.read();
			while (c != -1) {
				builder.append((char) c);
				c = in.read();
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Fail to load resource " + resource + " by " + type.getName(), e);
		}
		return builder.toString();
	}
}
