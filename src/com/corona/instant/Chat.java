/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

/**
 * <p>the chat is used to send text message to remote contact </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Chat {

	/**
	 * @return who is chatting with
	 */
	String getParticipant();

	/**
	 * @return whether participant is online or not
	 */
	boolean isOnline();
	
	/**
	 * @param message the message text
	 * @throws InstantException if fail to send message to remote contact
	 */
	void send(String message) throws InstantException;
	
	/**
	 * close this chat
	 */
	void close();
	
	/**
	 * @param <T> the value type
	 * @param key the key
	 * @param value the value of key for chat
	 */
	<T> void setValue(String key, T value);
	
	/**
	 * @param <T> the value type
	 * @param key the key
	 * @param defaultValue the default value of key in chat
	 * @return the value
	 */
	<T> T getValue(String key, T defaultValue);
	
	/**
	 * @param listener the chat listener
	 */
	void addChatListener(ChatListener listener);
	
	/**
	 * @param listener the chat listener
	 */
	void removeChatListener(ChatListener listener);
}
