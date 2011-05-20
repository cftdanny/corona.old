/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.data;

import com.corona.data.annotation.Entity;
import com.corona.data.annotation.Id;
import com.corona.data.annotation.PrimaryKey;

/**
 * <p>TABLE TORDMST </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Entity (
		primaryKey = @PrimaryKey("ORDRID")
)
public class TORDMST {

	/**
	 * id
	 */
	private Long ordrid;
	
	/**
	 * no
	 */
	private String ordrno;
	
	/**
	 * @return the oRDRID
	 */
	@Id public Long getORDRID() {
		return ordrid;
	}
	
	/**
	 * @param oRDRID the oRDRID to set
	 */
	public void setORDRID(final Long oRDRID) {
		ordrid = oRDRID;
	}

	/**
	 * @return the oRDRNO
	 */
	public String getORDRNO() {
		return ordrno;
	}
	
	/**
	 * @param oRDRNO the oRDRNO to set
	 */
	public void setORDRNO(final String oRDRNO) {
		ordrno = oRDRNO;
	}
}
