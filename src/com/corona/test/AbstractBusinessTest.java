/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.data.ConnectionManager;
import com.corona.data.Transaction;

/**
 * <p>This test is used to test business object. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AbstractBusinessTest {

	/**
	 * the context manager factory
	 */
	private ContextManagerFactory contextManagerFactory;
	
	/**
	 * the current context manager
	 */
	private ContextManager contextManager;
	
	/**
	 * the connection manager
	 */
	private ConnectionManager connectionManager;
	
	/**
	 * the transaction
	 */
	private Transaction transaction;
	
	@BeforeClass
	public void startup() {
		
	}
	
	@AfterClass
	public void shutdown() {
		
	}
	
	@BeforeTest public void begin() {
		
	}
	
	@AfterTest public void end() {
		
	}

	/**
	 * @return the context manager factory
	 */
	public ContextManagerFactory getContextManagerFactory() {
		return this.contextManagerFactory;
	}
	
	
}
