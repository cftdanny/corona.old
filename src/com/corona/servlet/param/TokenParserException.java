/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

/**
 * <p>This exception will be thrown if there is error when parse parameter name or expression. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TokenParserException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 313235196612424237L;

	/**
	 * @param message the message
	 */
	public TokenParserException(final String message) {
		super(message);
	}

	/**
	 * @param message the message
	 * @param cause parent exception
	 */
	public TokenParserException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
