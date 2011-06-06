/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import net.sf.jml.Email;
import net.sf.jml.MsnMessenger;

/**
 * <p>The commander that is used to process command from MSN client </p>
 *
 * @author $Author$
 * @version $Id$
 */
class MessagerCommander {

	/**
	 * the MSN Messenger handler
	 */
	private Messenger parent;
	
	/**
	 * @param parent the parent MSN Messenger handler
	 */
	MessagerCommander(final Messenger parent) {
		this.parent = parent;
	}
	
	/**
	 * @param messenger the MSN Messenger
	 * @param email the email
	 * @param command the command
	 */
	void process(final MsnMessenger messenger, final Email email, final String command) {
		
		try {
			if ("HELLO".equals(command.toUpperCase().trim())) {
				this.parent.getClients().add(email);
				messenger.sendText(email, "Welcome, " + email.getEmailAddress());
			} else if ("BYE".equals(command.toUpperCase().trim())) {
				this.parent.getClients().remove(email);
				messenger.sendText(email, "Bye, " + email.getEmailAddress());
			} else if ("HELP".equals(command.toUpperCase().trim())) {
				messenger.sendText(email, "Support commands: help, hello, bye.");
			}
		} catch (Exception e) {
			this.parent.getClients().remove(email);
		}
	}
}
