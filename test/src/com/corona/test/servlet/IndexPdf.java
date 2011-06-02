/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import com.corona.context.annotation.Optional;
import com.corona.servlet.annotation.Param;
import com.corona.servlet.annotation.Pdf;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.WebResource;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

/**
 * <p>This producer is used to create PDF content </p>
 *
 * @author $Author$
 * @version $Id$
 */
@WebResource
public class IndexPdf {

	/**
	 * name
	 */
	@Param("username") @Optional private String username = "shanghai";
	
	/**
	 * password
	 */
	@Param("password") @Optional private String password = "123456";
	
	/**
	 * create document
	 */
	@Same("/index.pdf")
	@Pdf("produce") public void create() {
		// in real world, we will collect data by parameters from data
	}

	/**
	 * @param document document
	 * @throws DocumentException if fail
	 */
	public void produce(final Document document) throws DocumentException {
		
		// create PDF document by parameter from default or URL
		document.add(new Paragraph("=> Name: "));  
		document.add(new Paragraph("      " + this.username));
		document.add(new Paragraph("=> Password: "));
		document.add(new Paragraph("      " + this.password));
	}
}
