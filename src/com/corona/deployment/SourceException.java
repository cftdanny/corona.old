/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.deployment;

import com.corona.util.StringUtil;

/**
 * <p>This exception will be thrown if fail to load class as descriptor from resource file. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SourceException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -3917271417436961306L;

	/**
	 * @param cause parent exception
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public SourceException(final String pattern, final Throwable cause, final Object... arguments) {
		super(StringUtil.format(pattern, arguments), cause);
	}

	/**
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public SourceException(final String pattern, final Object... arguments) {
		super(StringUtil.format(pattern, arguments));
	}
}
