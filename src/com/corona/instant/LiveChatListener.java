/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

import net.sf.jml.MsnContact;
import net.sf.jml.MsnMessenger;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.MsnUserStatus;
import net.sf.jml.event.MsnAdapter;
import net.sf.jml.message.MsnInstantMessage;

/**
 * <p>The message listener for Windows Live Messenger </p>
 *
 * @author $Author$
 * @version $Id$
 */
class LiveChatListener extends MsnAdapter {

	/**
	 * the live chat
	 */
	private LiveChat chat;
	
	/**
	 * @param chat the chat for Windows Live Messenger
	 */
	LiveChatListener(final LiveChat chat) {
		this.chat = chat;
	}

	/**
	 * {@inheritDoc}
	 * @see net.sf.jml.event.MsnAdapter#instantMessageReceived(
	 * 	net.sf.jml.MsnSwitchboard, net.sf.jml.message.MsnInstantMessage, net.sf.jml.MsnContact
	 * )
	 */
	public void instantMessageReceived(
			final MsnSwitchboard switchboard, final MsnInstantMessage message, final MsnContact contact) {
		
		if (this.chat.getContact().equals(contact.getEmail())) {
			this.chat.getChatListenerSupport().fireMessage(this.chat, message.getContent());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see net.sf.jml.event.MsnAdapter#contactStatusChanged(net.sf.jml.MsnMessenger, net.sf.jml.MsnContact)
	 */
	@Override
	public void contactStatusChanged(final MsnMessenger messenger, final MsnContact contact) {
		
		if (this.chat.getContact().equals(contact.getEmail())) {
			
			this.chat.setOnline(contact.getStatus() == MsnUserStatus.ONLINE);
			if (this.chat.isOnline()) {
				this.chat.getChatListenerSupport().fireOnline(this.chat);
			} else {
				this.chat.getChatListenerSupport().fireOffline(this.chat);
			}
		}
	}
}
