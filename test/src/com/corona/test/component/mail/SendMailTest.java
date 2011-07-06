/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.component.mail;

import org.testng.annotations.Test;

import com.corona.component.mail.InternetMailManager;
import com.corona.component.mail.Message;

/**
 * <p>Test send mail </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SendMailTest {

	/**
	 * @throws Exception if fail
	 */
	@Test public void tesSendMail() throws Exception {
		
		InternetMailManager mailManager = new InternetMailManager();
		
		mailManager.setSmtpServerName("smtp.gmail.com");
		mailManager.setSmtpPort(587);
		mailManager.getProperties().put("mail.smtp.starttls.enable", "true");
		
		mailManager.setUsername("");
		mailManager.setPassword("");
		
		Message message = new Message();
		message.setSubject("Test");
		message.setText("test");
		message.setTo("");
		
		mailManager.send(message);
	}
}
