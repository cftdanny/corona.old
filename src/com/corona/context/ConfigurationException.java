/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import com.corona.util.StringUtil;

/**
 * <p>This exception will be thrown when there is a configuration error when initialize context manager
 * factory runtime environment. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ConfigurationException extends RuntimeException {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 5019299000332815557L;

	/**
	 * @param pattern the message pattern about configuration exception
	 * @param arguments the argument value in the message pattern
	 */
	public ConfigurationException(final String pattern, final Object... arguments) {
		super(StringUtil.format(pattern, arguments));
	}
	
	/**
	 * @param cause the parent exception
	 * @param pattern the message pattern about configuration exception
	 * @param arguments the argument value in the message pattern
	 */
	public ConfigurationException(final String pattern, final Throwable cause, final Object... arguments) {
		super(StringUtil.format(pattern, arguments), cause);
	}
}
