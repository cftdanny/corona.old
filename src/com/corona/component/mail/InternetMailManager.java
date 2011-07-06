/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.component.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.corona.util.StringUtil;

/**
 * <p>This mail manager will create Java mail session by host and user name  </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class InternetMailManager extends AbstractMailManager {
	
	/**
	 * the user name
	 */
	private String username;
	
	/**
	 * the password
	 */
	private String password;
	
	/**
	 * the additional mail session properties
	 */
	private Properties properties = new Properties();
	
	/**
	 * @return the SMTP server name for mail service
	 */
	public String getSmtpServerName() {
		return this.properties.getProperty("mail.smtp.host");
	}

	/**
	 * @param smtpServerName the SMTP server name for mail service to set
	 */
	public void setSmtpServerName(final String smtpServerName) {
		this.properties.put("mail.smtp.host", smtpServerName);
	}
	
	/**
	 * @return the SMTP port for mail service
	 */
	public int getSmtpPort() {
		
		if (this.properties.contains("mail.smtp.port")) {
			return Integer.parseInt(this.properties.getProperty("mail.smtp.port"));
		} else {
			return 25;
		}
	}
	
	/**
	 * @param smtpPort the SMTP port for mail service to set
	 */
	public void setSmtpPort(final int smtpPort) {
		this.properties.put("mail.smtp.port", Integer.toString(smtpPort));
	}

	/**
	 * @return the default from mail address
	 */
	public String getFrom() {
		return this.properties.getProperty("mail.smtp.from");
	}

	/**
	 * @param from the default from mail address
	 */
	public void setFrom(final String from) {
		this.properties.put("mail.smtp.from", from);
	}

	/**
	 * @return the POP3 server name for mail service
	 */
	public String getPop3ServerName() {
		return this.properties.getProperty("mail.pop3.host");
	}
	
	/**
	 * @param pop3ServerName the POP3 server name for mail service to set
	 */
	public void setPop3ServerName(final String pop3ServerName) {
		this.properties.put("mail.pop3.host", pop3ServerName);
	}

	/**
	 * @return the POP3 port for mail service
	 */
	public int getPop3Port() {

		if (this.properties.contains("mail.pop3.port")) {
			return Integer.parseInt(this.properties.getProperty("mail.pop3.port"));
		} else {
			return 110;
		}
	}
	
	/**
	 * @param pop3Port the POP3 port for mail service to set
	 */
	public void setPop3Port(final int pop3Port) {
		this.properties.put("mail.pop3.port", Integer.toString(pop3Port));
	}

	/**
	 * @return the user name
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username the user name to set
	 */
	public void setUsername(final String username) {
		this.username = username;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}
	
	/**
	 * @return the additional mail session properties
	 */
	public Properties getProperties() {
		return properties;
	}
	
	/**
	 * @return the timeout for sending or receiving mail
	 */
	public int getTimeout() {
		
		if (this.properties.contains("mail.smtp.timeout")) {
			return Integer.parseInt(this.properties.getProperty("mail.smtp.timeout"));
		} else {
			return -1;
		}
	}
	
	/**
	 * @param milisecond the timeout for sending or receiving mail to set
	 */
	public void setTimeout(final int milisecond) {
		this.properties.put("mail.smtp.timeout", Integer.toString(milisecond));
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.component.mail.AbstractMailManager#getSession()
	 */
	@Override
	protected Session getSession() throws MailException {

		// create mail session according to whether user name set or not
		if (StringUtil.isBlank(this.username)) {
			return Session.getInstance(properties);
		} else {
			
			properties.put("mail.smtp.auth", true);
			return Session.getInstance(properties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
		}
	}
}
