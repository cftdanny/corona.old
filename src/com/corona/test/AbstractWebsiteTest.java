/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.Key;
import com.corona.data.ConnectionManager;
import com.corona.data.Transaction;
import com.corona.data.TransactionManager;
import com.corona.servlet.ApplicationLoader;

/**
 * <p>This test is used to test web page by an embedded JETTY server. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AbstractWebsiteTest {

	/**
	 * the configuration file for testing
	 */
	private static final String TEST_CONFIG_FILE = "/testing.properties";
	
	/**
	 * the key in properties file to load testing modules
	 */
	private static final String TEST_MODULES_KEY = "testing.modules";

	/**
	 * the JETTY server
	 */
	private Server server;
	
	/**
	 * the web app context
	 */
	private WebAppContext webAppContext;
	
	/**
	 * the current context manager
	 */
	private ContextManager contextManager;
	
	/**
	 * the current connection manager
	 */
	private ConnectionManager connectionManager;
	
	/**
	 * the current transaction
	 */
	private Transaction transaction;
	
	/**
	 * @throws Exception if fail to start JETTY server
	 */
	@BeforeClass public void startup() throws Exception {

		// create server, use free TCP port and testing web context
		this.server = new Server();
		this.server.addConnector(this.getConnector());
		this.server.setHandler(this.getWebAppContext());
		this.server.start();
	}

	/**
	 * @return the JEETY connector
	 * @exception Exception if fail to create JETTY connector
	 */
	private Connector getConnector() throws Exception {
		
		// find a free TCP socket port
		ServerSocket socket = new ServerSocket(0);
		int port = socket.getLocalPort();
		socket.close();

		Connector connector = new SocketConnector();
		connector.setPort(port);
		return connector;
	}

	/**
	 * @return the configuration file for testing
	 */
	protected String getTestConfigFile() {
		return TEST_CONFIG_FILE;
	}

	/**
	 * @return the testing web application context
	 */
	protected WebAppContext getWebAppContext() {
		
		if (this.webAppContext == null) {
			
			this.webAppContext = new WebAppContext();
			this.webAppContext.setDescriptor("./WEB-INF/web.xml");
			this.webAppContext.setResourceBase("./WebContent");
			this.webAppContext.setContextPath("/");
			this.webAppContext.setParentLoaderPriority(true);
		}
		return this.webAppContext;
	}
	
	/**
	 * @throws Exception if fail to stop JETTY server
	 */
	@AfterClass public void shutdown() throws Exception {
		this.server.stop();
	}

	/**
	 * @throws Exception if fail to release method testing resource
	 */
	@AfterMethod public void after() throws Exception {
		
		// check if transaction is active. If yes, roll back
		if ((this.transaction != null) && this.transaction.isActive()) {
			this.transaction.rollback();
		}
		this.transaction = null;
		
		if (this.connectionManager != null) {
			this.connectionManager.close();
		}
		this.connectionManager = null;
		
		if (this.contextManager != null) {
			this.contextManager.close();
		}
		this.contextManager = null;
	}
	
	/**
	 * @return the HTTP service port
	 */
	protected int getPort() {
		return this.server.getConnectors()[0].getPort();
	}

	/**
	 * @return the SERVLET context
	 */
	protected ServletContext getServletContext() {
		return this.getWebAppContext().getServletContext();
	}
	
	/**
	 * @return the current context manager factory
	 */
	protected ContextManagerFactory getContextManagerFactory() {
		return (ContextManagerFactory) this.getServletContext().getAttribute(ApplicationLoader.CONTEXT);
	}
	
	/**
	 * @return the predefined variables or context that will merge to new created context manager
	 */
	@SuppressWarnings("rawtypes")
	protected Map<Key, Object> getContext() {
		return new HashMap<Key, Object>();
	}

	/**
	 * @return the current context manager
	 */
	protected ContextManager getContextManager() {
		
		if (this.contextManager == null) {
			this.contextManager = this.getContextManagerFactory().create(this.getContext());
		}
		return this.contextManager;
	}
	
	/**
	 * @return the JTA transaction manager
	 */
	private TransactionManager getTransactionManager() {
		return this.contextManager.get(new Key<TransactionManager>(TransactionManager.class), false);
	}
	
	/**
	 * create a new transaction
	 */
	protected void begin() {
		
		// check if transaction is created or not. If yes, throw exception
		if (this.transaction != null) {
			throw new IllegalStateException("Transaction is created already");
		}
		
		// make sure context manager is created. If not create, will create it
		if (this.contextManager == null) {
			this.getContextManagerFactory();
		}
		
		// if connection manager is not created, create it in order to start transaction
		if (this.connectionManager == null) {
			this.connectionManager = this.contextManager.get(ConnectionManager.class);
		}
		
		// test whether use JTA transaction or not
		TransactionManager transactionManager = this.getTransactionManager();
		if (transactionManager != null) {
			this.transaction = transactionManager.getTransaction();
		} else {
			this.transaction = this.connectionManager.getTransaction();
		}
		this.transaction.begin();
	}
	
	/**
	 * commit transaction
	 */
	protected void commit() {
		this.transaction.commit();
		this.transaction = null;
	}
	
	/**
	 * roll back transaction
	 */
	public void rollback() {
		this.transaction.rollback();
		this.transaction = null;
	}
	
	/**
	 * @return the context path
	 */
	protected String getContextPath() {
		return this.getWebAppContext().getContextPath();
	}
	
	/**
	 * @param path the relative path
	 * @return the request path
	 */
	protected String createRequestPath(final String path) {
		
		String contextPath = this.getContextPath();
		if (contextPath.endsWith("/")) {
			return "http://127.0.0.1" + this.getContextPath() + path;
		} else {
			return "http://127.0.0.1" + this.getContextPath() + "/" + path;
		}
	}
	
	@Test public void test() {
		this.begin();
	}
}
