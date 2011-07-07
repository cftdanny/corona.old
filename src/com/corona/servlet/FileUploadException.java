/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

/**
 * Thrown when an exception occurs while uploading a file. 
 *  
 * @author Shane Bryzak
 */
public class FileUploadException extends RuntimeException {
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -4070015406007403914L;

	/**
	 * construct this exception
	 */
	public FileUploadException() {
		this(null, null);
	}

	/**
	 * @param message the exception message
	 */
	public FileUploadException(final String message) {
		this(message, null);
	}

	/**
	 * @param message the exception message
	 * @param cause the parent exception
	 */
	public FileUploadException(final String message, final Throwable cause) {
		super(message, cause);
	}   
}
