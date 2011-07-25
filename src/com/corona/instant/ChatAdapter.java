/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

/**
 * <p>The adapter class of chat listener </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ChatAdapter implements ChatListener {

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.ChatListener#message(com.corona.instant.Chat, java.lang.String)
	 */
	@Override
	public void message(final Chat chat, final String message) {
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.ChatListener#offline(com.corona.instant.Chat)
	 */
	@Override
	public void offline(final Chat chat) {
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.ChatListener#online(com.corona.instant.Chat)
	 */
	@Override
	public void online(final Chat chat) {
	}
}
