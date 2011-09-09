/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.deployment;

import com.corona.util.StringUtil;

/**
 * <p>This exception will be thrown if fail to create component instance in context manager. 
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ScanningException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 941043045168821160L;

	/**
	 * @param cause parent exception
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public ScanningException(final String pattern, final Throwable cause, final Object... arguments) {
		super(StringUtil.format(pattern, arguments), cause);
	}

	/**
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public ScanningException(final String pattern, final Object... arguments) {
		super(StringUtil.format(pattern, arguments));
	}
}
