/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.upload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.corona.context.annotation.Optional;
import com.corona.servlet.annotation.Controller;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.Upload;

/**
 * <p>test upload file </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class UploadBean {

	/**
	 * the upload file 1
	 */
	@Upload @Optional private InputStream file1;
	
	/**
	 * the upload file 2
	 */
	private byte[] file2;
	
	/**
	 * the value 1 for file 1
	 */
	private String value1;
	
	/**
	 * the value 2 for file 2
	 */
	private String value2;
	
	/**
	 * the value 3 for file 3
	 */
	private String value3;
	
	/**
	 * @param file2 the file2 to set
	 */
	@Upload public void setFile2(final byte[] file2) {
		this.file2 = file2;
	}
	
	/**
	 * @return the value1
	 */
	public String getValue1() {
		return value1;
	}
	
	/**
	 * @return the value2
	 */
	public String getValue2() {
		return value2;
	}
	
	/**
	 * @return the value3
	 */
	public String getValue3() {
		return value3;
	}

	/**
	 * nothing
	 */
	@Same("/upload.html")
	@FreeMaker("/upload.ftl")
	public void upload() {
		// do nothing
	}
	
	/**
	 * @param file3 file 3
	 * @return this
	 * @exception IOException if IO error
	 */
	@Same("/uploaded.html")
	@FreeMaker("/uploaded.ftl")
	public UploadBean uploaded(@Upload("file3") final InputStream file3) throws IOException {

		ByteArrayOutputStream output = new ByteArrayOutputStream(); 
		for (int b = file1.read(); b != -1; b = file1.read()) {
			output.write(b);
		}
		file1.close();
		this.value1 = new String(output.toByteArray());

		this.value2 = new String(this.file2);
		
		output = new ByteArrayOutputStream(); 
		for (int b = file3.read(); b != -1; b = file3.read()) {
			output.write(b);
		}
		file3.close();
		this.value3 = new String(output.toByteArray());
		
		return this;
	}
}
