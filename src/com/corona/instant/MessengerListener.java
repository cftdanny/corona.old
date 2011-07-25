/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

/**
 * <p>The listener is used to listen messenger event </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface MessengerListener {

	/**
	 * @param chat the new created chat
	 */
	void chatCreated(Chat chat);
}
