/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

import java.util.Vector;

/**
 * <p>The support class for messenger listener </p>
 *
 * @author $Author$
 * @version $Id$
 */
class MessengerListenerSupport {

	/**
	 * the chat listeners
	 */
	private Vector<MessengerListener> listeners = new Vector<MessengerListener>();
	
	/**
	 * @param listener the messenger listener
	 */
	void add(final MessengerListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * @param listener the messenger listener
	 */
	void remove(final MessengerListener listener) {
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
	 */
	void fireChatCreated(final Chat chat) {
		
		for (MessengerListener listener : this.listeners) {
			listener.chatCreated(chat);
		}
	}
}
