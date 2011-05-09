/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;


/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Test {

	public static void main(String[] args) throws Exception {
		
		 ConnectionConfiguration connectionConfig = new ConnectionConfiguration(
				 "talk.google.com", 5222, "chengyousoft.com"
		);
		 XMPPConnection connection = new XMPPConnection(connectionConfig);
		 
		 connection.connect();
		 connection.login("administrator@chengyousoft.com", "wwq543j0");
		 
		 ChatManager chatmanager = connection.getChatManager();
		 
		 Chat chat = chatmanager.createChat("cftdanny@gmail.com", new MessageListener() {

			@Override
			public void processMessage(Chat arg0, Message arg1) {
			}
			 
		 });
		 chat.sendMessage("asd");
		 chat.sendMessage("asd");
		 chat.sendMessage("asd");
		 chat.sendMessage("aaaaa");
	}
}
