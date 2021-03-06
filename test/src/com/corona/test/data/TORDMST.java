/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.data;

import java.sql.Date;

import com.corona.data.annotation.Entity;
import com.corona.data.annotation.Id;
import com.corona.data.annotation.PrimaryKey;
import com.corona.data.annotation.UniqueKey;
import com.corona.data.annotation.Index;

/**
 * <p>TABLE TORDMST </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Entity (
		primaryKey = @PrimaryKey("ORDRID"),
		uniqueKeys = {
				@UniqueKey(id = TORDMST.UK_ORDRNO, columns = "ORDRNO")
		},
		indexes = {
				@Index(id = TORDMST.IX_ORDDAT, columns = "ORDDAT")
		}
)
public class TORDMST {

	/**
	 * the unique key 1
	 */
	public static final int UK_ORDRNO = 1;
	
	/**
	 * the index
	 */
	public static final int IX_ORDDAT = 1;
	
	/**
	 * id
	 */
	private Long ordrid;
	
	/**
	 * no
	 */
	private String ordrno;
	
	/**
	 * quantity
	 */
	private Integer ordqty;
	
	/**
	 * date
	 */
	private Date orddat;
	
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
	
	/**
	 * @return the oRDQTY
	 */
	public Integer getORDQTY() {
		return ordqty;
	}
	
	/**
	 * @param oRDQTY the oRDQTY to set
	 */
	public void setORDQTY(final Integer oRDQTY) {
		ordqty = oRDQTY;
	}
	
	/**
	 * @return the oRDDAT
	 */
	public Date getORDDAT() {
		return orddat;
	}

	/**
	 * @param oRDDAT the oRDDAT to set
	 */
	public void setORDDAT(final Date oRDDAT) {
		orddat = oRDDAT;
	}
}
