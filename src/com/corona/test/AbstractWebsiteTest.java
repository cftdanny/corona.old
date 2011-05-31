/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test;

import java.net.ServerSocket;

import javax.servlet.ServletContext;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.corona.context.ContextManagerFactory;
import com.corona.servlet.ApplicationLoader;

/**
 * <p>This test is used to test web page by an embedded JETTY server. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AbstractWebsiteTest {

	/**
	 * the JETTY server
	 */
	private Server server;
	
	/**
	 * the web app context
	 */
	private WebAppContext webAppContext;
	
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
	@AfterClass public void stop() throws Exception {
		this.server.stop();
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
}
