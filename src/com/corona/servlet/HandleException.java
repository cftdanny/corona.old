/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.text.MessageFormat;

/**
 * <p>The exception that will be thrown if fail to handle HTTP request </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class HandleException extends Exception {

	/**
	 * The Serial Version UID
	 */
	private static final long serialVersionUID = 5351131732702042513L;

	/**
	 * @param pattern the message pattern about configuration exception
	 * @param arguments the argument value in the message pattern
	 */
	public HandleException(final String pattern, final Object... arguments) {
		super(MessageFormat.format(pattern, arguments));
	}
	
	/**
	 * @param cause the parent exception
	 * @param pattern the message pattern about configuration exception
	 * @param arguments the argument value in the message pattern
	 */
	public HandleException(final String pattern, final Throwable cause, final Object... arguments) {
		super(MessageFormat.format(pattern, arguments), cause);
	}
}
