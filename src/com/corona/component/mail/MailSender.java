/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.component.mail;

/**
 * <p>This mail sender is used to send mail </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface MailSender {

	/**
	 * @param message the mail message to be sent
	 * @throws MailException if fail to send mail
	 */
	void send(Message message) throws MailException;
}
