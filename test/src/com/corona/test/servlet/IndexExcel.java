/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.corona.context.annotation.Optional;
import com.corona.servlet.annotation.Excel;
import com.corona.servlet.annotation.Param;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.WebResource;

/**
 * <p>This page is used to test EXCEL producer </p>
 *
 * @author $Author$
 * @version $Id$
 */
@WebResource
public class IndexExcel {

	/**
	 * name
	 */
	@Param("username") @Optional private String username = "shanghai";
	
	/**
	 * password
	 */
	@Param("password") @Optional private String password = "123456";
	
	/**
	 * create excel data
	 */
	@Same("/index.xls")
	@Excel("produce") public void create() {
		// in real world, we will collect data by parameters from data
	}

	/**
	 * @param workbook document
	 * @throws Exception if fail 
	 */
	public void produce(final WritableWorkbook workbook) throws Exception {
		
		WritableSheet sheet = workbook.createSheet("TEST", 0);
		
		Label label = new Label(0, 0, "Name: ");
		sheet.addCell(label);

		label = new Label(1, 0, this.username);
		sheet.addCell(label);

		label = new Label(2, 0, "Password: ");
		sheet.addCell(label);

		label = new Label(3, 0, this.password);
		sheet.addCell(label);
	}
}
