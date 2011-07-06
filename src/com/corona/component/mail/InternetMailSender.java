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
public class InternetMailSender extends AbstractMailSender {
	
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
	public String getHostName() {
		return this.properties.getProperty("mail.smtp.host");
	}

	/**
	 * @param hostName the SMTP server name for mail service to set
	 */
	public void setHostName(final String hostName) {
		this.properties.put("mail.smtp.host", hostName);
	}
	
	/**
	 * @return the SMTP port for mail service
	 */
	public int getPort() {
		
		if (this.properties.contains("mail.smtp.port")) {
			return Integer.parseInt(this.properties.getProperty("mail.smtp.port"));
		} else {
			return 25;
		}
	}
	
	/**
	 * @param port the SMTP port for mail service to set
	 */
	public void setPort(final int port) {
		this.properties.put("mail.smtp.port", Integer.toString(port));
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
	 * @see com.corona.component.mail.AbstractMailSender#getSession()
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
