/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

import java.util.Properties;

import com.corona.instant.Messenger.State;

import net.sf.jml.Email;
import net.sf.jml.MsnContact;
import net.sf.jml.MsnUserStatus;

/**
 * <p>Send text message to others by Windows Live Messenger </p>
 *
 * @author $Author$
 * @version $Id$
 */
class LiveChat implements Chat {

	/**
	 * the Windows Live Messenger
	 */
	private LiveMessenger messenger;
	
	/**
	 * the contact in this chat
	 */
	private Email contact;

	/**
	 * whether participant is online or not
	 */
	private boolean online = false;

	/**
	 * the chat listener support
	 */
	private ChatListenerSupport chatListenerSupport = new ChatListenerSupport();
	
	/**
	 * all chat listeners
	 */
	private LiveChatListener chatListener;
	
	/**
	 * the properties
	 */
	private Properties properties = new Properties();
	
	/**
	 * @param messenger the Windows Live Messenger
	 * @param contact the contact of this chat
	 */
	LiveChat(final LiveMessenger messenger, final String contact) {
		
		// store Windows Live Messenger and contacts 
		this.messenger = messenger;
		this.contact = Email.parseStr(contact);

		// check contact online state
		MsnContact msnContact = messenger.getMsnMessenger().getContactList().getContactByEmail(this.contact);
		if ((msnContact != null) && (msnContact.getStatus() == MsnUserStatus.ONLINE)) {
			this.online = true;
		}
		
		// add listener to listen message arrive
		this.chatListener = new LiveChatListener(this);
		this.messenger.getMsnMessenger().addListener(this.chatListener);
	}
	
	/**
	 * @return the contact
	 */
	Email getContact() {
		return contact;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Chat#getParticipant()
	 */
	@Override
	public String getParticipant() {
		return this.contact.getEmailAddress();
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
	 * @param online the online to set
	 */
	void setOnline(final boolean online) {
		this.online = online;
	}

	/**
	 * @return the chat listener support
	 */
	ChatListenerSupport getChatListenerSupport() {
		return chatListenerSupport;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Chat#send(java.lang.String)
	 */
	@Override
	public void send(final String messenge) {
		
		if (this.messenger.getState() != State.CONNECTED) {
			return;
		}
		if (!this.isOnline()) {
			return;
		}
		
		this.messenger.getMsnMessenger().sendText(this.contact, messenge);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Chat#close()
	 */
	@Override
	public void close() {
		
		this.messenger.getMsnMessenger().removeMessageListener(this.chatListener);
		
		this.chatListenerSupport.clear();
		this.messenger.getChats().remove(this);
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
		return this.properties.contains(key) ? (T) this.properties.get(key) : defaultValue;
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
