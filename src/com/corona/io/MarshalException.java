/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io;

import java.text.MessageFormat;

/**
 * <p>This exception will be raised if fail to marshal Java object to output stream. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class MarshalException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 4863540643371801661L;


	/**
	 * @param pattern the message or pattern pattern
	 * @param arguments the argument value in pattern
	 */
	public MarshalException(final String pattern, final Object... arguments) {
		super(MessageFormat.format(pattern, arguments));
	}
	
	/**
	 * @param cause the parent exception
	 * @param pattern the message or pattern pattern
	 * @param arguments the argument value in pattern
	 */
	public MarshalException(final String pattern, final Throwable cause, final Object... arguments) {
		super(MessageFormat.format(pattern, arguments), cause);
	}
}
