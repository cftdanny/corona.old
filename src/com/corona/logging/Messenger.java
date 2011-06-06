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

import net.sf.jml.Email;
import net.sf.jml.MsnContact;
import net.sf.jml.MsnMessenger;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.event.MsnMessageAdapter;
import net.sf.jml.event.MsnMessengerAdapter;
import net.sf.jml.impl.MsnMessengerFactory;
import net.sf.jml.message.MsnInstantMessage;

/**
 * <p>This handler will send logging messages to MSN Messenger contact. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Messenger extends Handler {

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
	 * the MSN Messenger
	 */
	private MsnMessenger messenger = null;
	
	/**
	 * the commander
	 */
	private MessagerCommander commander = new MessagerCommander(this);
	
	/**
	 * the contact list
	 */
	private Set<Email> clients = new HashSet<Email>();
	
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
	 * create handler and load default configuration
	 */
	public Messenger() {
		
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
		String value = LogManager.getLogManager().getProperty(Messenger.class.getName() + "." + name);
		return value == null ? defaultValue : value;
	}
	
	/**
	 * @return the user name of MSN
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the user name of MSN to set
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
	 * @return the remote clients
	 */
	Set<Email> getClients() {
		return clients;
	}

	/**
	 * try to connect MSN server
	 */
	private void open() {
		
		// Has tried to connect MSN server few seconds before, don't try again
		if ((new Date().getTime() - this.lastLoginTime.getTime()) < 60000L) {
			return;
		}
		
		// create MSN messenger instance and in order to connect MSN server
		this.messenger = MsnMessengerFactory.createMsnMessenger(this.username, this.password);
		this.messenger.setLogIncoming(true);
		this.messenger.setLogOutgoing(true);
		
		// listen log in and log out event
		this.messenger.addMessengerListener(new MsnMessengerAdapter() {
			public void exceptionCaught(final MsnMessenger msnMessenger, final Throwable throwable) {
				close();
			}
			public void loginCompleted(final MsnMessenger msnMessenger) {
				state = State.CONNECTED;
			}
			public void logout(final MsnMessenger msnMessenger) {
				state = State.DISCONNECT;
			}
		});
		
		// listen event from other contact
		this.messenger.addMessageListener(new MsnMessageAdapter() {
			public void instantMessageReceived(
					final MsnSwitchboard board, final MsnInstantMessage message, final MsnContact contact) {
				commander.process(messenger, contact.getEmail(), message.getContent());
			}
			public void offlineMessageReceived(
					final String body, final String type, final String encoding, final MsnContact contact) {
				clients.remove(contact.getEmail());
			}
		});
		
		// remember last connect time, state and then try to connect MSN server
		this.state = State.CONNECTING;
		this.lastLoginTime = new Date();
		this.clients.clear();
		this.messenger.login();
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
			
			String message = this.getFormatter().format(record);
			for (Email email : this.clients) {
				try {
					this.messenger.sendText(email, message);
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
		
		if (this.messenger != null) {
			try {
				this.messenger.logout();
			} catch (Exception e) {
				this.getErrorManager().error("Fail to log out MSN!", e, ErrorManager.GENERIC_FAILURE);
			}
		}
		this.state = State.DISCONNECT;
	}
}
