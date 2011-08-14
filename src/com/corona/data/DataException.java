/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import com.corona.util.StringUtil;

/**
 * <p>This exception will be thrown if there is catchable exception in database module. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DataException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 7776368351012447396L;

	/**
	 * @param cause parent exception
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public DataException(final String pattern, final Throwable cause, final Object... arguments) {
		super(StringUtil.format(pattern, arguments), cause);
	}

	/**
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public DataException(final String pattern, final Object... arguments) {
		super(StringUtil.format(pattern, arguments));
	}
}
