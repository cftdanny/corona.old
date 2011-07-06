/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.component.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * the content type
	 */
	private String contentType;
	
	/**
	 * the content
	 */
	private Object content;
	
	/**
	 * mail from
	 */
	private String from;
	
	/**
	 * send mail to
	 */
	private String to;
	
	/**
	 * mail cc address
	 */
	private String cc;
	
	/**
	 * when this will is sent
	 */
	private Date sentDate = new Date();
	
	/**
	 * the attachments
	 */
	private List<Attachment> attachments = new ArrayList<Attachment>();
	
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
	 * @return the content type
	 */
	public String getContentType() {
		return contentType;
	}
	
	/**
	 * @param contentType the content type to set
	 */
	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}
	
	/**
	 * @return the content
	 */
	public Object getContent() {
		return content;
	}
	
	/**
	 * @param content the content to set
	 */
	public void setContent(final Object content) {
		this.content = content;
	}

	/**
	 * @param text the mail main body text to set
	 */
	public void setText(final String text) {
		this.contentType = "text/plain";
		this.content = text;
	}
	
	/**
	 * @param html the mail main body html to set
	 */
	public void setHtml(final String html) {
		this.contentType = "text/html";
		this.content = html;
	}
	
	/**
	 * @return when this will is sent
	 */
	public Date getSentDate() {
		return sentDate;
	}

	/**
	 * @param sentDate when this will is sent to set
	 */
	public void setSentDate(final Date sentDate) {
		this.sentDate = sentDate;
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
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}
	
	/**
	 * @param cc the cc to set
	 */
	public void setCc(final String cc) {
		this.cc = cc;
	}
	
	/**
	 * @param attachment the attachment to be added
	 */
	public void addAttachment(final Attachment attachment) {
		this.attachments.add(attachment);
	}
	
	/**
	 * @return the attachments
	 */
	public List<Attachment> getAttachments() {
		return attachments;
	}
}
