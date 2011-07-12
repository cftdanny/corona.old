/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import java.text.MessageFormat;

/**
 * <p>This exception will be thrown if fail to create component instance in context manager. 
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AsyncException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 941043045168821160L;

	/**
	 * @param cause parent exception
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public AsyncException(final String pattern, final Throwable cause, final Object... arguments) {
		super(MessageFormat.format(pattern, arguments), cause);
	}

	/**
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public AsyncException(final String pattern, final Object... arguments) {
		super(MessageFormat.format(pattern, arguments));
	}
}
