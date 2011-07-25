/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

/**
 * <p>Messenger for GTalk </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class GTalkMessenger implements Messenger {

	/**
	 * the state for connecting with GTalk server
	 */
	private State state = State.DISCONNECT;

	/**
	 * the XMPP connection
	 */
	private XMPPConnection connection = null;

	/**
	 * the domain name
	 */
	private String domain = "gmail.com";
	
	/**
	 * the user name
	 */
	private String username;
	
	/**
	 * the password
	 */
	private String password;
	
	/**
	 * the last time try to connect GTalk
	 */
	private Date lastLoginTime = new Date(0);

	/**
	 * all created chats by this messenger
	 */
	private Map<String, GTalkChat> chats = new HashMap<String, GTalkChat>();

	/**
	 * the GTalk messenger listener
	 */
	private GTalkMessengerListener messengerListener = new GTalkMessengerListener(this);;
	
	/**
	 * the messenger listener support
	 */
	private MessengerListenerSupport messengerListenerSupport = new MessengerListenerSupport();
	
	/**
	 * @return the connection
	 */
	XMPPConnection getConnection() {
		return connection;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Messenger#getState()
	 */
	@Override
	public State getState() {
		return this.state;
	}
	
	/**
	 * @param state the state to set
	 */
	void setState(final State state) {
		this.state = state;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}
	
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(final String domain) {
		this.domain = domain;
	}

	/**
	 * @return the user name
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username the user name to set
	 */
	public void setUsername(final String username) {
		this.username = username;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Messenger#open()
	 */
	@Override
	public void open() throws InstantException {
		
		// Has tried to connect GTalk server few seconds before, don't try again
		if ((new Date().getTime() - this.lastLoginTime.getTime()) < 60000L) {
			return;
		}

		// create TGalk client and in order to connect to GTalk server
		ConnectionConfiguration config = new ConnectionConfiguration("talk.google.com", 5222, this.domain);
		config.setSASLAuthenticationEnabled(false);

		// remember last connect time, state and then try to connect GTalk server
		this.state = State.CONNECTING;
		this.lastLoginTime = new Date();

		// create gtalk connect
		XMPPConnection.addConnectionCreationListener(this.messengerListener);
		this.connection = new XMPPConnection(config);
		try {
			this.connection.connect();
		} catch (XMPPException e) {
			throw new InstantException("Fail to connect to google gtalk server talk.google.com", e);
		}

		// install all listeners for opened connection
		this.connection.addConnectionListener(this.messengerListener);
		this.connection.getRoster().addRosterListener(this.messengerListener);
		this.connection.getChatManager().addChatListener(this.messengerListener);

		// try to connect and log in gtalk server
		this.chats.clear();
		this.messengerListenerSupport.clear();
		try {
			this.connection.login(this.username, this.password);
		} catch (XMPPException e) {
			throw new InstantException("Fail to log in gtalk server with account [{0}]", e, this.username);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Messenger#close()
	 */
	@Override
	public void close() {
		
		// close all children chats from GTalk messenger
		for (GTalkChat chat : this.chats.values().toArray(new GTalkChat[0])) {
			chat.dispose();
		}
		this.chats.clear();
		this.messengerListenerSupport.clear();

		// logout and disconnect from GTalk server
		if ((this.connection != null) && (this.state != State.DISCONNECT)) {

			// clear all running environment that are set in open method
			this.connection.getChatManager().removeChatListener(this.messengerListener);
			this.connection.getRoster().removeRosterListener(this.messengerListener);
			this.connection.removeConnectionListener(this.messengerListener);
			XMPPConnection.removeConnectionCreationListener(this.messengerListener);
	
			this.connection.disconnect();
		}
		this.state = State.DISCONNECT;
	}

	/**
	 * @param from the from name sent by client
	 * @return the account name
	 */
	private String getAccount(final String from) {
		
		int index = from.indexOf("/");
		if (index != -1) {
			return from.substring(0, index);
		} else {
			return from;
		}
	}
	
	/**
	 * @param contact the contact
	 * @return the GTalk chat with specified contact
	 */
	GTalkChat findChat(final String contact) {
		return this.chats.get(this.getAccount(contact));
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Messenger#createChat(java.lang.String)
	 */
	@Override
	public Chat createChat(final String contact) {
		
		// find whether chat exists with lower case contact
		GTalkChat gtalkChat = this.findChat(contact);
		if (gtalkChat != null) {
			 return gtalkChat;
		}
		
		// create GTalk chat
		return createChat(this.connection.getChatManager().createChat(contact, null));
	}
	
	/**
	 * @param chat the chat
	 * @return GTalk chat
	 */
	Chat createChat(final org.jivesoftware.smack.Chat chat) {
		
		GTalkChat gtalkChat = this.findChat(chat.getParticipant());
		if (gtalkChat != null) {
			gtalkChat.dispose();
			this.chats.remove(chat.getParticipant());
		}
		
		gtalkChat = new GTalkChat(this, chat);
		this.chats.put(this.getAccount(chat.getParticipant()), gtalkChat);
		
		this.messengerListenerSupport.fireChatCreated(gtalkChat);
		
		Presence presence = this.connection.getRoster().getPresence(chat.getParticipant());
		if (presence != null) {
			gtalkChat.setOnline(presence.isAvailable());
		}
		
		return gtalkChat;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Messenger#addMessengerListener(com.corona.instant.MessengerListener)
	 */
	@Override
	public void addMessengerListener(final MessengerListener listener) {
		this.messengerListenerSupport.add(listener);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Messenger#removeMessengerListener(com.corona.instant.MessengerListener)
	 */
	@Override
	public void removeMessengerListener(final MessengerListener listener) {
		this.messengerListenerSupport.remove(listener);
	}
}
