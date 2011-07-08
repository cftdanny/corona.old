/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.multipart;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>The HTTP SERVLET request is used to support multipart request </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface MultipartRequest extends HttpServletRequest {

	/**
	 * @param name the name of upload file
	 * @return the bytes of upload file content
	 */
	byte[] getFileBytes(String name);
	
	/**
	 * @param name the name of upload file
	 * @return the input stream of upload file
	 */
	InputStream getFileInputStream(String name);
	
	/**
	 * @param name the name of upload file
	 * @return the file content
	 */
	String getFileContentType(String name);
	
	/**
	 * @param name the name of upload file
	 * @return the file name if created temporary file
	 */
	String getFileName(String name);

	/**
	 * @param name the name of upload file
	 * @return the file size
	 */
	int getFileSize(String name);
}
