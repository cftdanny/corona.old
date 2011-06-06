/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

/**
 * <p>This handler is used to send logging message to an XMPP client. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class GTalk extends Handler implements ChatManagerListener, MessageListener {

	/**
	 * <p>The state for whether connect to MSN server </p>
	 */
	private enum State {
		
		/**
		 * does not connect to MSN server
		 */
		DISCONNECT,
		
		/**
		 * is connecting to MSN server
		 */
		CONNECTING,
		
		/**
		 * already connected to MSN server
		 */
		CONNECTED
	}
	
	/**
	 * the state of connecting MSN server
	 */
	private State state = State.DISCONNECT;

	/**
	 * the XMPP connection
	 */
	private XMPPConnection connection = null;

	/**
	 * the GTalk commander
	 */
	private GTalkCommander commander = new GTalkCommander(this);
	
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
	 * all connected chats
	 */
	private Set<Chat> clients = new HashSet<Chat>();
	
	/**
	 * load all properties for this handler that are defined in properties
	 */
	public GTalk() {
		
		this.domain = this.getProperty("domain", "gmail.com");
		this.username = this.getProperty("username", null);
		this.password = this.getProperty("password", null);
		try {
			this.setLevel(Level.parse(this.getProperty("level", "SEVERE")));
		} catch (Exception e) {
			this.setLevel(Level.SEVERE);
		}
		this.setFormatter(new DisplayFormatter());
	}

	/**
	 * @param name the property value that is configured in logging configuration
	 * @param defaultValue the default value
	 * @return the value
	 */
	private String getProperty(final String name, final String defaultValue) {
		String value = LogManager.getLogManager().getProperty(GTalk.class.getName() + "." + name);
		return value == null ? defaultValue : value;
	}

	/**
	 * @return the domain name
	 */
	public String getDomain() {
		return domain;
	}
	
	/**
	 * @param domain the domain name to set
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
	 * @return the clients
	 */
	Set<Chat> getClients() {
		return clients;
	}

	/**
	 * try to connect GTalk server
	 */
	private void open() {
		
		// Has tried to connect GTalk server few seconds before, don't try again
		if ((new Date().getTime() - this.lastLoginTime.getTime()) < 60000L) {
			return;
		}

		// create TGalk client and in order to connect to GTalk server
		ConnectionConfiguration config = new ConnectionConfiguration("talk.google.com", 5222, this.domain);
		config.setSASLAuthenticationEnabled(false);
		
		// try to connect to GTalk server
		this.connection = new XMPPConnection(config);
		try {
			this.connection.connect();
			this.connection.login(this.username, this.password);
		} catch (XMPPException e) {
			this.connection = null;
		}

		// remember last connect time, state and then try to connect GTalk server
		this.lastLoginTime = new Date();
		this.clients.clear();
		if (this.connection != null) {
			// create connection, chat listener and listen connection and chat event
			this.connection.addConnectionListener(new ConnectionListener() {
				public void connectionClosed() {
					close();
				}
				public void connectionClosedOnError(final Exception e) {
					close();
				}
				public void reconnectionFailed(final Exception seconds) {
					close();
				}
				public void reconnectingIn(final int seconds) {
					state = State.CONNECTING;
				}
				public void reconnectionSuccessful() {
					state = State.CONNECTED;
				}
			});
			this.connection.getChatManager().addChatListener(this);
		}
		this.state = (this.connection != null) ? State.CONNECTED : State.DISCONNECT;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.ChatManagerListener#chatCreated(org.jivesoftware.smack.Chat, boolean)
	 */
	@Override
	public void chatCreated(final Chat chat, final boolean createdLocally) {
		
		if (!createdLocally) {
			chat.addMessageListener(this);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.jivesoftware.smack.MessageListener#processMessage(
	 * 	org.jivesoftware.smack.Chat, org.jivesoftware.smack.packet.Message
	 * )
	 */
	@Override
	public void processMessage(final Chat chat, final Message message) {
		
		if (message.getType().equals(Message.Type.error)) {
			this.clients.remove(chat);
		} else {
			this.commander.process(chat, message);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
	 */
	@Override
	public void publish(final LogRecord record) {
		
		// if doesn't connect MSN server, will try to connect
		if (record.getLevel().intValue() < this.getLevel().intValue()) {
			return;
		} else if (this.state != State.CONNECTED) {
			this.open();
		} else if (this.clients.size() > 0) {
			
			Message message = new Message();
			message.setBody(this.getFormatter().format(record));
			for (Chat chat : this.clients) {
				try {
					chat.sendMessage(message);
				} catch (Exception e) {
					this.getErrorManager().error("Fail send message", e, ErrorManager.GENERIC_FAILURE);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see java.util.logging.Handler#flush()
	 */
	@Override
	public void flush() {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see java.util.logging.Handler#close()
	 */
	@Override
	public void close() {
		
		if (this.connection != null) {
			try {
				this.connection.disconnect();
			} catch (Exception e) {
				this.getErrorManager().error("Fail to log out GTalk!", e, ErrorManager.GENERIC_FAILURE);
			}
		}
		this.state = State.DISCONNECT;
	}
}
