/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.pdf;

import com.corona.servlet.annotation.Pdf;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.WebResource;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
@WebResource
public class CreatePdfContent {

	/**
	 * name
	 */
	private String username;
	
	/**
	 * password
	 */
	private String password;
	
	/**
	 * @return the user
	 */
	@Same("/test.pdf")
	@Pdf("createPdf") public CreatePdfContent findData() {
		
		this.username = "abcd";
		this.password = "1234";
		return this;
	}

	/**
	 * @param document document
	 * @param data data
	 * @throws DocumentException if fail
	 */
	public void createPdf(final Document document, final CreatePdfContent data) throws DocumentException {
		
		document.add(new Paragraph("Name: "));  
		document.add(new Paragraph(this.username));
		document.add(new Paragraph("Password: "));
		document.add(new Paragraph(this.password));
	}
}
