/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.instant;

import org.testng.annotations.Test;

import com.corona.instant.Chat;
import com.corona.instant.ChatAdapter;
import com.corona.instant.GTalkMessenger;

/**
 * <p>Test google chat </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class GoogleGTalkMessengerTest {

	/**
	 * @throws Exception if fail
	 */
	@Test public void testWindowsLiveMessenger() throws Exception {
		
		GTalkMessenger messenger = new GTalkMessenger();
		messenger.setDomain("");
		messenger.setUsername("");
		messenger.setPassword("");
		messenger.open();

		try {
			Thread.sleep(1 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Chat chat = messenger.createChat("");
		chat.send("hello");
		chat.send("world");
		chat.addChatListener(new ChatAdapter() {
			public void message(final Chat chat, final String message) {
				System.out.println("CHAT: " + message);
			}
			public void offline(final Chat chat) {
				System.out.println("CHAT: " + chat.getParticipant() + " is offline");
			}
			public void online(final Chat chat) {
				System.out.println("CHAT: " + chat.getParticipant() + " is online");
			}
		});
		
		try {
			Thread.sleep(180 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		messenger.close();
	}
}
