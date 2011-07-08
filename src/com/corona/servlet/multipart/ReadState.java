/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.multipart;

/**
 * <p>the state for parse multipart request stream. </p>
 *
 * @author $Author$
 * @version $Id$
 */
enum ReadState {

	/**
	 * read to boundary
	 */
	BOUNDARY,

	/**
	 * read to header
	 */
	HEADERS,

	/**
	 * read to data
	 */
	DATA
}
