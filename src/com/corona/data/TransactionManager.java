/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

/**
 * <p>this transaction manager is used to create a container managed transaction in order to 
 * control data source resources. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TransactionManager {

	/**
	 * the user transaction JNDI name
	 */
	private String name = "java:comp/UserTransaction";
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the new transaction
	 */
	public Transaction getTransaction() {
		
		try {
			return new ContainerManagedTransaction((UserTransaction) new InitialContext().lookup(this.name));
		} catch (NamingException e) {
			throw new DataRuntimeException("Fail to create transaction with resource [{0}]", this.name);
		}
	}
}
