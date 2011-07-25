/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jml.MsnMessenger;
import net.sf.jml.impl.MsnMessengerFactory;

/**
 * <p>Chat with others by Windows Live Messenger </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class LiveMessenger implements Messenger {

	/**
	 * the state of connecting MSN server
	 */
	private State state = State.DISCONNECT;
	
	/**
	 * the MSN Messenger
	 */
	private MsnMessenger messenger = null;

	/**
	 * the messenger listener
	 */
	private LiveMessengerListener messengerListener;
	
	/**
	 * the user account of MSN
	 */
	private String username;
	
	/**
	 * the password of MSN
	 */
	private String password;
	
	/**
	 * the last time to log in MSN
	 */
	private Date lastLoginTime = new Date(0);

	/**
	 * all created chats by this messenger
	 */
	private Map<String, LiveChat> chats = new HashMap<String, LiveChat>();
	
	/**
	 * the messenger listener support
	 */
	private MessengerListenerSupport messengerListenerSupport = new MessengerListenerSupport();

	/**
	 * @return the user account of MSN
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username the user account of MSN to set
	 */
	public void setUsername(final String username) {
		this.username = username;
	}
	
	/**
	 * @return the password of MSN
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password of MSN to set
	 */
	public void setPassword(final String password) {
		this.password = password;
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
	public void setState(final State state) {
		this.state = state;
	}

	/**
	 * @return the Windows Live Messenger
	 */
	MsnMessenger getMsnMessenger() {
		return this.messenger;
	}
	
	/**
	 * @return all chats created by this messenger
	 */
	Map<String, LiveChat> getChats() {
		return chats;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Messenger#open()
	 */
	@Override
	public void open() {
		
		// Has tried to connect MSN server few seconds before, don't try again
		if ((new Date().getTime() - this.lastLoginTime.getTime()) < 60000L) {
			return;
		}
		
		// create MSN messenger instance and in order to connect MSN server
		this.messenger = MsnMessengerFactory.createMsnMessenger(this.username, this.password);
		this.messenger.setLogIncoming(true);
		this.messenger.setLogOutgoing(true);
		
		// listen log in and log out event
		this.messengerListener = new LiveMessengerListener(this);
		this.messenger.addMessengerListener(this.messengerListener);
		this.messenger.addMessageListener(this.messengerListener);
		
		// remember last connect time, state and then try to connect MSN server
		this.state = State.CONNECTING;
		this.lastLoginTime = new Date();
		
		this.chats.clear();
		this.messengerListenerSupport.clear();
		
		// log in
		this.messenger.login();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Messenger#close()
	 */
	@Override
	public void close() {
		
		// close all child chat from Windows Live Messenger
		for (LiveChat chat : this.chats.values().toArray(new LiveChat[0])) {
			chat.close();
		}
		this.chats.clear();
		this.messengerListenerSupport.clear();
		
		// send log out message to remote server
		this.messenger.removeMessengerListener(this.messengerListener);
		this.messenger.removeMessageListener(this.messengerListener);
		
		if ((this.messenger != null) && (this.state == State.CONNECTED)) {
			this.messenger.logout();
		}
		this.state = State.DISCONNECT;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.instant.Messenger#createChat(java.lang.String)
	 */
	@Override
	public Chat createChat(final String contact) {
		
		LiveChat chat = new LiveChat(this, contact);
		this.chats.put(contact, chat);
		
		this.messengerListenerSupport.fireChatCreated(chat);
		
		return chat;
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
