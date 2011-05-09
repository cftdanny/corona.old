/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DataDriverManager {

	public ConnectionManagerFactory build(final String family, final String uri) {
		return this.build(family, uri, "", "");
	}
	
	public ConnectionManagerFactory build(
			final String family, final String uri, final String username, final String password) {
		return null;
	}
}
