/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.instant;

import org.testng.annotations.Test;

import com.corona.instant.Chat;
import com.corona.instant.ChatAdapter;
import com.corona.instant.LiveMessenger;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class WindowsLiveMessengerTest {

	/**
	 * @throws Exception if fail
	 */
	@Test public void testWindowsLiveMessenger() throws Exception {
		
		LiveMessenger messenger = new LiveMessenger();
		messenger.setUsername("");
		messenger.setPassword("");
		messenger.open();
		try {
			Thread.sleep(30 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Chat chat = messenger.createChat("");
		chat.send("hello");
		chat.send("world");
		chat.addChatListener(new ChatAdapter() {
			public void message(final Chat chat, final String message) {
				System.out.print(message);
			}
		});
		try {
			Thread.sleep(30 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		messenger.close();
	}
}
