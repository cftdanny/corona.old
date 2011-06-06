/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

/**
 * <p>The commander that is used to process command from MSN client </p>
 *
 * @author $Author$
 * @version $Id$
 */
class GTalkCommander {

	/**
	 * the MSN Messenger handler
	 */
	private GTalk parent;
	
	/**
	 * @param parent the parent GTalk handler
	 */
	GTalkCommander(final GTalk parent) {
		this.parent = parent;
	}
	
	/**
	 * @param chat the chat
	 * @param message message
	 */
	void process(final Chat chat, final Message message) {
		
		try {
			if ("HELLO".equals(message.getBody().toUpperCase().trim())) {
				this.parent.getClients().add(chat);
				chat.sendMessage("Welcome, " + chat.getParticipant());
			} else if ("BYE".equals(message.getBody().toUpperCase().trim())) {
				this.parent.getClients().remove(chat);
				chat.sendMessage("Bye, " + chat.getParticipant());
			} else if ("HELP".equals(message.getBody().toUpperCase().trim())) {
				chat.sendMessage("Support commands: help, hello, bye.");
			}
		} catch (Exception e) {
			this.parent.getClients().remove(chat);
		}
	}
}
