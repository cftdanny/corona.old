/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

/**
 * <p>This listener is used to listen chat message </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ChatListener {

	/**
	 * @param chat the chat session
	 * @param message the message
	 */
	void message(Chat chat, String message);
	
	/**
	 * @param chat the chat session
	 */
	void online(Chat chat);
	
	/**
	 * @param chat the chat session
	 */
	void offline(Chat chat);
}
