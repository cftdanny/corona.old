/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.component.mail;

import org.testng.annotations.Test;

import com.corona.component.mail.Attachment;
import com.corona.component.mail.InternetMailSender;
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
		
		InternetMailSender mailManager = new InternetMailSender();
		
		mailManager.setHostName("smtp.gmail.com");
		mailManager.setPort(587);
		mailManager.getProperties().put("mail.smtp.starttls.enable", "true");
		
		mailManager.setUsername("1111@222.com");
		mailManager.setPassword("XXX");
		
		Message message = new Message();
		message.setSubject("Test");
		message.setHtml("test");
		message.setTo("AAA <BBB@hotmail.com>");
		message.setCc("CCC@hotmail.com");
		
		message.addAttachment(new Attachment("1.txt", "asfasd"));
		message.addAttachment(new Attachment("2.html", new byte[] {32, 44, 83}, "text/html"));
		
		mailManager.send(message);
	}
}
