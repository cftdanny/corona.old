/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.component.mail;

/**
 * <p>the mail message </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Message {
	
	/**
	 * the mail subject
	 */
	private String subject;

	/**
	 * the mail main body text
	 */
	private String text;
	
	/**
	 * mail from
	 */
	private String from;
	
	/**
	 * send mail to
	 */
	private String to;
	
	/**
	 * @return the mail subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * @param subject the mail subject to set
	 */
	public void setSubject(final String subject) {
		this.subject = subject;
	}
	
	/**
	 * @return the mail main body text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @param text the mail main body text to set
	 */
	public void setText(final String text) {
		this.text = text;
	}
	
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	
	/**
	 * @param to the to to set
	 */
	public void setTo(final String to) {
		this.to = to;
	}
	
	/**
	 * @return the mail from
	 */
	public String getFrom() {
		return from;
	}
	
	/**
	 * @param from the mail from to set
	 */
	public void setFrom(final String from) {
		this.from = from;
	}
}
