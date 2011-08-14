/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.crypto;

import com.corona.util.StringUtil;

/**
 * <p>This exception will be raised if fail to marshal Java object to output stream. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class CypherException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -5772212327068277840L;

	/**
	 * @param pattern the message or pattern pattern
	 * @param arguments the argument value in pattern
	 */
	public CypherException(final String pattern, final Object... arguments) {
		super(StringUtil.format(pattern, arguments));
	}
	
	/**
	 * @param cause the parent exception
	 * @param pattern the message or pattern pattern
	 * @param arguments the argument value in pattern
	 */
	public CypherException(final String pattern, final Throwable cause, final Object... arguments) {
		super(StringUtil.format(pattern, arguments), cause);
	}
}
