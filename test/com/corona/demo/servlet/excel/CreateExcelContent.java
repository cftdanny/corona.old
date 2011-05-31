/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.demo.servlet.excel;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.corona.servlet.annotation.Excel;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.WebResource;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
@WebResource
public class CreateExcelContent {

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
	@Same("/test.xls")
	@Excel("createExcel") public CreateExcelContent findData() {
		
		this.username = "abcd";
		this.password = "1234";
		return this;
	}

	/**
	 * @param workbook document
	 * @param data data
	 * @throws Exception if fail 
	 */
	public void createExcel(final WritableWorkbook workbook, final CreateExcelContent data) throws Exception {
		
		WritableSheet sheet = workbook.createSheet("TEST", 0);
		
		Label label = new Label(0, 0, "Name: ");
		sheet.addCell(label);

		label = new Label(1, 0, data.username);
		sheet.addCell(label);

		label = new Label(2, 0, "Password: ");
		sheet.addCell(label);

		label = new Label(3, 0, data.password);
		sheet.addCell(label);
	}
}
