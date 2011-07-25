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
	 * @param messenge the message text
	 * @throws InstantException if fail to send message to remote contact
	 */
	void send(String messenge) throws InstantException;
	
	/**
	 * close this chat
	 */
	void close();
	
	/**
	 * @param listener the chat listener
	 */
	void addChatListener(ChatListener listener);
	
	/**
	 * @param listener the chat listener
	 */
	void removeChatListener(ChatListener listener);
}
