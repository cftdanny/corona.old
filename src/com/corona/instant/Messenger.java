/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.instant;

/**
 * <p>The messenger that chat with others by instant server </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Messenger {

	/**
	 * <p>The state for working with instant server </p>
	 */
	public enum State {
		
		/**
		 * try to disconnect from instance server
		 */
		DISCONNECTING,
		
		/**
		 * does not connect to instant server
		 */
		DISCONNECT,
		
		/**
		 * is connecting to instant server
		 */
		CONNECTING,
		
		/**
		 * already connected to instant server
		 */
		CONNECTED
	}

	/**
	 * @return the state with instant server
	 */
	State getState();
	
	/**
	 * open connection with instant server
	 * 
	 * @throws InstantException if fail connect to server
	 */
	void open() throws InstantException;
	
	/**
	 * disconnect from instant server
	 */
	void close();
	
	/**
	 * @param contact who will chat with
	 * @return the chat
	 */
	Chat createChat(String contact);
	
	/**
	 * @param listener the messenger listener
	 */
	void addMessengerListener(MessengerListener listener);
	
	/**
	 * @param listener the messenger listener
	 */
	void removeMessengerListener(MessengerListener listener);
}
