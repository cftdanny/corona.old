/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.component.mail;

import java.io.ByteArrayInputStream;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

/**
 * <p>The attachment </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Attachment {

	/**
	 * the file name
	 */
	private String fileName;
	
	/**
	 * the data source
	 */
	private DataSource dataSource;
	
	/**
	 * @param fileName the file name
	 * @param text the text
	 */
	public Attachment(final String fileName, final String text) {
		this(fileName, text, "text/plain");
	}

	/**
	 * @param fileName the file name
	 * @param content the content
	 * @param type the content type
	 */
	public Attachment(final String fileName, final String content, final String type) {

		this.fileName = fileName;
		try {
			this.dataSource = new ByteArrayDataSource(content, type);
		} catch (Exception e) {
			this.dataSource = null;
		}
	}
	
	/**
	 * @param fileName the file name
	 * @param data the content
	 * @param type the content type
	 */
	public Attachment(final String fileName, final byte[] data, final String type) {

		this.fileName = fileName;
		try {
			this.dataSource = new ByteArrayDataSource(new ByteArrayInputStream(data), type);
		} catch (Exception e) {
			this.dataSource = null;
		}
	}
	
	/**
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the data source
	 */
	DataSource getDataSource() {
		return this.dataSource;
	}
}
