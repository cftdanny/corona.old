/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.component.mail;

import javax.mail.Session;
import javax.naming.InitialContext;

/**
 * <p>This mail manager will use JNDI to get Java mail session </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class NamingMailSender extends AbstractMailSender {

	/**
	 * the JNDI name to lookup java mail
	 */
	private String name;
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.component.mail.AbstractMailSender#getSession()
	 */
	@Override
	protected Session getSession() throws MailException {
		
		try {
			return (Session) new InitialContext().lookup(this.name);
		} catch (Exception e) {
			throw new MailException("Fail to lookup mail session by JNDI [{0}]", e, this.name);
		}
	}

	/**
	 * @return the JNDI name to lookup java mail
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the JNDI name to lookup java mail to set
	 */
	public void setName(final String name) {
		this.name = name;
	}
}
