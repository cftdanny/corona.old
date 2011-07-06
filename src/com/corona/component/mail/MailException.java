/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.component.mail;

import java.text.MessageFormat;

/**
 * <p>This exception will be thrown if there is catchable exception in mail component module. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class MailException extends Exception {

	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = 7755203592164975801L;

	/**
	 * @param cause parent exception
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public MailException(final String pattern, final Throwable cause, final Object... arguments) {
		super(MessageFormat.format(pattern, arguments), cause);
	}

	/**
	 * @param pattern the pattern of error message
	 * @param arguments the arguments for pattern
	 */
	public MailException(final String pattern, final Object... arguments) {
		super(MessageFormat.format(pattern, arguments));
	}
}
