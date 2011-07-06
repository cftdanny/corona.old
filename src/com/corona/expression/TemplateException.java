/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.expression;

/**
 * <p>the exception if there is error when execute template </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TemplateException extends RuntimeException {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 6047805076199041325L;

	/**
	 * @param cause the parent cause
	 */
	public TemplateException(final Throwable cause) {
		super(cause);
	}
}
