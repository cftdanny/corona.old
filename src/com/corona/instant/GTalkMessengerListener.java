/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionCreationListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

/**
 * <p>This listener is used to listen event for GTalk messenger </p>
 *
 * @author $Author$
 * @version $Id$
 */
class GTalkMessengerListener implements 
	ConnectionCreationListener, ConnectionListener, RosterListener, ChatManagerListener {

	/**
	 * the GTalk messenger
	 */
	private GTalkMessenger messenger;
	
	/**
	 * @param messenger the GTalk messenger
	 */
	GTalkMessengerListener(final GTalkMessenger messenger) {
		this.messenger = messenger;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.ConnectionCreationListener#connectionCreated(org.jivesoftware.smack.Connection)
	 */
	@Override
	public void connectionCreated(final Connection connection) {
		
		if (connection.equals(this.messenger.getConnection())) {
			this.messenger.setState(Messenger.State.CONNECTED);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.ConnectionListener#connectionClosed()
	 */
	@Override
	public void connectionClosed() {
		this.messenger.close();
	}

	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.ConnectionListener#connectionClosedOnError(java.lang.Exception)
	 */
	@Override
	public void connectionClosedOnError(final Exception exception) {
		this.messenger.close();
	}

	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.ConnectionListener#reconnectingIn(int)
	 */
	@Override
	public void reconnectingIn(final int seconds) {
		this.messenger.setState(Messenger.State.CONNECTING);
	}

	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.ConnectionListener#reconnectionFailed(java.lang.Exception)
	 */
	@Override
	public void reconnectionFailed(final Exception exception) {
		this.messenger.close();
	}

	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.ConnectionListener#reconnectionSuccessful()
	 */
	@Override
	public void reconnectionSuccessful() {
		this.messenger.setState(Messenger.State.CONNECTED);
	}

	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.RosterListener#entriesAdded(java.util.Collection)
	 */
	@Override
	public void entriesAdded(final Collection<String> addresses) {
	}

	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.RosterListener#entriesDeleted(java.util.Collection)
	 */
	@Override
	public void entriesDeleted(final Collection<String> addresses) {
	}

	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.RosterListener#entriesUpdated(java.util.Collection)
	 */
	@Override
	public void entriesUpdated(final Collection<String> addresses) {
	}

	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.RosterListener#presenceChanged(org.jivesoftware.smack.packet.Presence)
	 */
	@Override
	public void presenceChanged(final Presence presence) {
		
		GTalkChat chat = this.messenger.findChat(presence.getFrom());
		if (chat != null) {
			
			if (presence.getType() == Presence.Type.available) {
				chat.setOnline(true);
				chat.getChatListenerSupport().fireOnline(chat);
			} else if (presence.getType() == Presence.Type.unavailable) {
				chat.setOnline(false);
				chat.getChatListenerSupport().fireOffline(chat);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.ChatManagerListener#chatCreated(org.jivesoftware.smack.Chat, boolean)
	 */
	@Override
	public void chatCreated(final Chat chat, final boolean createdLocally) {
		
		if (!createdLocally) {
			this.messenger.createChat(chat);
		}
	}
}
