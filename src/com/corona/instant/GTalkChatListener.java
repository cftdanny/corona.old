/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * <p>This listener is used to listen GTalk event </p>
 *
 * @author $Author$
 * @version $Id$
 */
class GTalkChatListener implements MessageListener {

	/**
	 * the GTalk chat
	 */
	private GTalkChat gtalkChat;
	
	/**
	 * @param gtalkChat the GTalk chat
	 */
	GTalkChatListener(final GTalkChat gtalkChat) {
		this.gtalkChat = gtalkChat;
	}

	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.MessageListener#processMessage(
	 * 	org.jivesoftware.smack.Chat, org.jivesoftware.smack.packet.Message
	 * )
	 */
	@Override
	public void processMessage(final Chat chat, final Message message) {
		this.gtalkChat.getChatListenerSupport().fireMessage(this.gtalkChat, message.getBody());
	}
}
