/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.component.mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.corona.util.StringUtil;

/**
 * <p>The default internet mail manager </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractMailManager implements MailManager {

	/**
	 * the mail address with name
	 */
	private static final Pattern ADDRESS = Pattern.compile("\\s*(.+?)\\s*<\\s*(.+?)\\s*>\\s*");
	
	/**
	 * @return the java mail session
	 * @exception MailException if fail to get Java mail session
	 */
	protected abstract Session getSession() throws MailException;

	/**
	 * {@inheritDoc}
	 * @see com.corona.component.mail.MailManager#send(com.corona.component.mail.Message)
	 */
	@Override
	public void send(final Message message) throws MailException {
		
		Session session = this.getSession();
		MimeMessage mimemessage = new MimeMessage(session);

		try {
			// set default mail from address if it is set
			String from = session.getProperty("mail.smtp.from");
			if (!StringUtil.isBlank(message.getFrom())) {
				from = message.getFrom();
			}
			if (StringUtil.isBlank(from)) {
				mimemessage.setFrom(this.getAddress(from));
			}
			
			// create mime mail message
			mimemessage.setSentDate(message.getSentDate());
			mimemessage.setSubject(message.getSubject());
			if (!StringUtil.isBlank(message.getTo())) {
				mimemessage.addRecipients(RecipientType.TO, this.getAddresses(message.getTo()));
			}
			if (!StringUtil.isBlank(message.getCc())) {
				mimemessage.addRecipients(RecipientType.CC, this.getAddresses(message.getCc()));
			}
			mimemessage.setContent(this.getMimeMultipart(message));
		} catch (Exception e) {
			throw new MailException("Fail to create mail message to be sent to SMTP server", e);
		}
		
		try {
			Transport.send(mimemessage);
		} catch (MessagingException e) {
			throw new MailException("Fail to send mail to SMTP server", e);
		}
	}
	
	/**
	 * @param message the message
	 * @return the message part
	 * @throws MessagingException if fail to create mail part
	 */
	private MimeMultipart getMimeMultipart(final Message message) throws MessagingException {
		
		MimeMultipart multipart = new MimeMultipart();
		
		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(message.getContent(), message.getContentType());
		multipart.addBodyPart(bodyPart);
		
		for (Attachment attachment : message.getAttachments()) {
			
			MimeBodyPart childPart = new MimeBodyPart();
			childPart.setFileName(attachment.getFileName());
			childPart.setDataHandler(new DataHandler(attachment.getDataSource()));
			
			multipart.addBodyPart(childPart);
		}
		
		return multipart;
	}
	
	/**
	 * @param name the mail address name
	 * @return the address
	 * @throws AddressException if address is invalid
	 * @throws UnsupportedEncodingException if address is invalid
	 */
	private Address getAddress(final String name) throws AddressException, UnsupportedEncodingException {
		
		if (!StringUtil.isBlank(name)) {
			Matcher matcher = ADDRESS.matcher(name);
			if (matcher.matches()) {
				return new InternetAddress(matcher.group(2), matcher.group(1));
			} else {
				return new InternetAddress(name);
			}
		} else {
			return null;
		}
	}
	
	/**
	 * @param names the mail address names
	 * @return the addresses
	 * @throws AddressException if address is invalid
	 * @throws UnsupportedEncodingException if address is invalid
	 */
	private Address[] getAddresses(final String names) throws AddressException, UnsupportedEncodingException {
		
		List<Address> addresses = new ArrayList<Address>();
		if (!StringUtil.isBlank(names)) {
			for (String name : names.split(",")) {
				
				Address address = this.getAddress(name);
				if (address != null) {
					addresses.add(address);
				}
			}
		}
		return addresses.toArray(new Address[0]);
	}
}
