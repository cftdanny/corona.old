/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import com.corona.util.StringUtil;

/**
 * <p>This exception will be thrown if fail to create component instance in context manager. 
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ValueException extends RuntimeException {

	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = 3055353355952445149L;

	/**
	 * @param cause parent exception
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public ValueException(final String pattern, final Throwable cause, final Object... arguments) {
		super(StringUtil.format(pattern, arguments), cause);
	}

	/**
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public ValueException(final String pattern, final Object... arguments) {
		super(StringUtil.format(pattern, arguments));
	}
}
