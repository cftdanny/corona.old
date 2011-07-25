/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

import java.util.Vector;

/**
 * <p>This support class is used to support chat listener </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ChatListenerSupport {

	/**
	 * the chat listeners
	 */
	private Vector<ChatListener> listeners = new Vector<ChatListener>();
	
	/**
	 * @param listener the chat listener
	 */
	void add(final ChatListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * @param listener the chat listener
	 */
	void remove(final ChatListener listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * clear all listeners
	 */
	void clear() {
		this.listeners.clear();
	}
	
	/**
	 * @param chat the chat
	 * @param message the message
	 */
	void fireMessage(final Chat chat, final String message) {
		
		for (ChatListener listener : this.listeners) {
			listener.message(chat, message);
		}
	}
	
	/**
	 * @param chat the chat
	 */
	void fireOnline(final Chat chat) {
		
		for (ChatListener listener : this.listeners) {
			listener.online(chat);
		}
	}

	/**
	 * @param chat the chat
	 */
	void fireOffline(final Chat chat) {
		
		for (ChatListener listener : this.listeners) {
			listener.offline(chat);
		}
	}
}
