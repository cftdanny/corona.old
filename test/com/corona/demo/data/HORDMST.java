/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.demo.data;

import com.corona.data.AbstractHome;
import com.corona.data.ConnectionManager;

/**
 * <p>HOME FOR TORDMST </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class HORDMST extends AbstractHome<TORDMST> {

	/**
	 * @param connectionManager the connection manager
	 */
	public HORDMST(final ConnectionManager connectionManager) {
		super(connectionManager, TORDMST.class);
	}
}
