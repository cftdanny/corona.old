/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

import com.corona.instant.Messenger.State;

import net.sf.jml.MsnContact;
import net.sf.jml.MsnMessenger;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.event.MsnAdapter;
import net.sf.jml.message.MsnInstantMessage;

/**
 * <p>This listener is used to listen Windows Live Messenger event </p>
 *
 * @author $Author$
 * @version $Id$
 */
class LiveMessengerListener extends MsnAdapter {

	/**
	 * Windows Live Messenger
	 */
	private LiveMessenger messenger;
	
	/**
	 * @param messenger Windows Live Messenger
	 */
	LiveMessengerListener(final LiveMessenger messenger) {
		this.messenger = messenger;
	}
	
	/**
	 * {@inheritDoc}
	 * @see net.sf.jml.event.MsnMessengerAdapter#exceptionCaught(net.sf.jml.MsnMessenger, java.lang.Throwable)
	 */
	public void exceptionCaught(final MsnMessenger msnMessenger, final Throwable throwable) {
		this.messenger.close();
	}

	/**
	 * {@inheritDoc}
	 * @see net.sf.jml.event.MsnMessengerAdapter#loginCompleted(net.sf.jml.MsnMessenger)
	 */
	public void loginCompleted(final MsnMessenger msnMessenger) {
		this.messenger.setState(State.CONNECTED);
	}
	
	/**
	 * {@inheritDoc}
	 * @see net.sf.jml.event.MsnMessengerAdapter#logout(net.sf.jml.MsnMessenger)
	 */
	public void logout(final MsnMessenger msnMessenger) {
		this.messenger.setState(State.DISCONNECT);
	}

	/**
	 * {@inheritDoc}
	 * @see net.sf.jml.event.MsnAdapter#instantMessageReceived(
	 * 	net.sf.jml.MsnSwitchboard, net.sf.jml.message.MsnInstantMessage, net.sf.jml.MsnContact
	 * )
	 */
	public void instantMessageReceived(
			final MsnSwitchboard switchboard, final MsnInstantMessage message, final MsnContact contact) {
		
		if (!this.messenger.getChats().containsKey(contact.getEmail().getEmailAddress())) {
		
			LiveChat newChat = (LiveChat) this.messenger.createChat(contact.getEmail().getEmailAddress());
			newChat.getChatListenerSupport().fireMessage(newChat, message.getContent());
		}
	}
}
