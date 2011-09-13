/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm;

import com.corona.util.StringUtil;

/**
 * <p>This exception will be thrown if fail to create component instance in context manager. 
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ProcessException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 7468758366539060219L;

	/**
	 * @param cause parent exception
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public ProcessException(final String pattern, final Throwable cause, final Object... arguments) {
		super(StringUtil.format(pattern, arguments), cause);
	}

	/**
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public ProcessException(final String pattern, final Object... arguments) {
		super(StringUtil.format(pattern, arguments));
	}
}
