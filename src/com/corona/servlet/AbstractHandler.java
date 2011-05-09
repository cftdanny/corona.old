/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

/**
 * <p>The helper handler that store matcher </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractHandler implements Handler {

	/**
	 * the matcher
	 */
	private Matcher matcher;
	
	/**
	 * @param matcher the matcher
	 */
	public AbstractHandler(final Matcher matcher) {
		this.matcher = matcher;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Handler#getMatcher()
	 */
	@Override
	public Matcher getMatcher() {
		return this.matcher;
	}
}
