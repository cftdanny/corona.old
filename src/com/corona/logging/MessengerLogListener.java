/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.corona.instant.Chat;
import com.corona.instant.ChatAdapter;
import com.corona.instant.Messenger;
import com.corona.instant.MessengerListener;

/**
 * <p>This listener will send log message to messenger </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class MessengerLogListener implements LogListener {

	/**
	 * whether send log message to client
	 */
	private static final String ENABLED = "enabled";
	
	/**
	 * the instant messenger
	 */
	private Messenger messenger;
	
	/**
	 * the chats
	 */
	private List<Chat> chats = new ArrayList<Chat>();
	
	/**
	 * @param properties the properties to create messenger
	 * @return the messenger
	 * @throws Exception if fail to create messenger
	 */
	protected abstract Messenger createMessenger(final Properties properties) throws Exception;

	/**
	 * @param properties the properties to create messenger
	 * @param type the type of messenger, for example GTalk, Live
	 * @param key the key of value
	 * @return the value
	 */
	protected String getProperty(final Properties properties, final String type, final String key) {
		return properties.getProperty(LogListeners.LISTENER_PROPERTY_KEY + type + "." + key);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.logging.LogListener#configure(java.util.Properties)
	 */
	@Override
	public void configure(final Properties properties) throws Exception {
		
		this.messenger = this.createMessenger(properties);
		this.messenger.addMessengerListener(new MessengerListener() {
			public void chatCreated(final Chat chat) {
				clientConnected(chat);
			}
		});
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.logging.LogListener#close()
	 */
	@Override
	public void close() {
		
		this.messenger.close();
		this.chats.clear();
	}

	/**
	 * @param chat the client chat
	 */
	private void clientConnected(final Chat chat) {
		
		if (!this.chats.contains(chat)) {
			
			this.chats.add(chat);
			chat.addChatListener(new ChatAdapter() {
				
				public void message(final Chat chat, final String message) {
					commandReceived(chat, message);
				}
			});
		}
	}
	
	/**
	 * @param chat the chat
	 * @param command the command
	 */
	private void commandReceived(final Chat chat, final String command) {
		
		try {
			if ("HELLO".equals(command.toUpperCase().trim())) {
				chat.setValue(ENABLED, true);
				chat.send("Welcome, " + chat.getParticipant());
			} else if ("BYE".equals(command.toUpperCase().trim())) {
				chat.setValue(ENABLED, false);
				chat.send("Bye, " + chat.getParticipant());
			} else if ("HELP".equals(command.toUpperCase().trim())) {
				chat.send("Support commands: help, hello, bye.");
			}
		} catch (Exception e) {
			this.chats.remove(chat);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.logging.LogListener#log(java.lang.String)
	 */
	@Override
	public void log(final String message) {
		
		for (Chat chat : this.chats.toArray(new Chat[0])) {
			
			if (chat.getValue(ENABLED, false)) {
				try {
					chat.send(message);
				} catch (Exception e) {
					this.chats.remove(chat);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.logging.LogListener#log(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void log(final String message, final Throwable cause) {
		
		StringWriter writer = new StringWriter();
		cause.printStackTrace(new PrintWriter(writer));
		this.log(message + "\r\n" + writer.toString());
	}
}
