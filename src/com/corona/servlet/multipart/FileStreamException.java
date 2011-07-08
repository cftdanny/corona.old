/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.multipart;

/**
 * Thrown when an exception occurs while uploading a file. 
 *  
 * @author $Author$
 * @version $Id$
 */
public class FileStreamException extends RuntimeException {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -5375445423775049490L;

	/**
	 * construct this exception
	 */
	public FileStreamException() {
		this(null, null);
	}

	/**
	 * @param message the exception message
	 */
	public FileStreamException(final String message) {
		this(message, null);
	}

	/**
	 * @param message the exception message
	 * @param cause the parent exception
	 */
	public FileStreamException(final String message, final Throwable cause) {
		super(message, cause);
	}   
}
