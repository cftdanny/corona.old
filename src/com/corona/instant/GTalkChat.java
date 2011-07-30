/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

import java.util.Properties;

import org.jivesoftware.smack.XMPPException;

/**
 * <p>The chat for GTalk </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class GTalkChat implements Chat {

	/**
	 * the GTalk messenger
	 */
	private GTalkMessenger messenger;
	
	/**
	 * the GTalk chat
	 */
	private org.jivesoftware.smack.Chat chat;
	
	/**
	 * whether participant is online or not
	 */
	private boolean online = false;
	
	/**
	 * the GTalk chat message
	 */
	private GTalkChatListener messageListener;
	
	/**
	 * the chat listener support
	 */
	private ChatListenerSupport chatListenerSupport = new ChatListenerSupport();
	
	/**
	 * the properties
	 */
	private Properties properties = new Properties();

	/**
	 * @param messenger the GTalk messenger
	 * @param chat the GTalk chat
	 */
	GTalkChat(final GTalkMessenger messenger, final org.jivesoftware.smack.Chat chat) {
		
		this.messenger = messenger;
		this.chat = chat;
		
		this.messageListener = new GTalkChatListener(this);
		this.chat.addMessageListener(this.messageListener);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Chat#getParticipant()
	 */
	@Override
	public String getParticipant() {
		return this.chat.getParticipant();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Chat#isOnline()
	 */
	@Override
	public boolean isOnline() {
		return this.online;
	}
	
	/**
	 * @param online whether participant is online or not
	 */
	void setOnline(final boolean online) {
		this.online = online;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Chat#send(java.lang.String)
	 */
	@Override
	public void send(final String messenge) throws InstantException {
		
		if (this.messenger.getState() != Messenger.State.CONNECTED) {
			return;
		}
		if (!this.isOnline()) {
			return;
		}

		try {
			this.chat.sendMessage(messenge);
		} catch (XMPPException e) {
			throw new InstantException("Fail send message to [{0}]", e, this.chat.getParticipant());
		}
	}

	/**
	 * dispose this chat
	 */
	void dispose() {
		this.close();
		this.chat.removeMessageListener(this.messageListener);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Chat#close()
	 */
	@Override
	public void close() {
		this.chatListenerSupport.clear();
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Chat#setValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T> void setValue(final String key, final T value) {
		this.properties.put(key, value);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Chat#getValue(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getValue(final String key, final T defaultValue) {
		return this.properties.containsKey(key) ? (T) this.properties.get(key) : defaultValue;
	}

	/**
	 * @return the chat listener support
	 */
	ChatListenerSupport getChatListenerSupport() {
		return chatListenerSupport;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Chat#addChatListener(com.corona.instant.ChatListener)
	 */
	@Override
	public void addChatListener(final ChatListener listener) {
		this.chatListenerSupport.add(listener);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Chat#removeChatListener(com.corona.instant.ChatListener)
	 */
	@Override
	public void removeChatListener(final ChatListener listener) {
		this.chatListenerSupport.remove(listener);
	}
}
