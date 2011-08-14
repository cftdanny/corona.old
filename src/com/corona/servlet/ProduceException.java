/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import com.corona.util.StringUtil;

/**
 * <p>This exception will be thrown if fail to produce HTTP response content. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ProduceException extends Exception {

	/**
	 * The Serial Version UID
	 */
	private static final long serialVersionUID = -526139442947003750L;

	/**
	 * @param pattern the message pattern about configuration exception
	 * @param arguments the argument value in the message pattern
	 */
	public ProduceException(final String pattern, final Object... arguments) {
		super(StringUtil.format(pattern, arguments));
	}
	
	/**
	 * @param cause the parent exception
	 * @param pattern the message pattern about configuration exception
	 * @param arguments the argument value in the message pattern
	 */
	public ProduceException(final String pattern, final Throwable cause, final Object... arguments) {
		super(StringUtil.format(pattern, arguments), cause);
	}
}
