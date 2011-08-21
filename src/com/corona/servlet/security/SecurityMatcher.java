/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.MatchResult;
import com.corona.servlet.Matcher;
import com.corona.util.XmlUtil;

/**
 * <p>This matcher is used to check whether user is logged in before trys to access resource </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SecurityMatcher implements Matcher {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(SecurityMatcher.class);
	
	/**
	 * the matching priority
	 */
	private int priority = 1;
	
	/**
	 * the status code to send if not matched any pages
	 */
	private int statusCode = -1;
	
	/**
	 * the redirect to page if not matched any pages
	 */
	private String redirectPage = null;
	
	/**
	 * all resource patterns
	 */
	private List<ResourcePatterns> resourcePatterns;
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Matcher#getPriority()
	 */
	@Override
	public int getPriority() {
		return this.priority;
	}

	/**
	 * @param priority the matching priority
	 */
	public void setPriority(final int priority) {
		this.priority = priority;
	}

	/**
	 * @param servletContext the SERVLET context to load security pages
	 * @return all defined resource patterns in security page
	 */
	private List<ResourcePatterns> getResourcePatterns(final ServletContext servletContext) {
		
		List<ResourcePatterns> patterns = new ArrayList<ResourcePatterns>();
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Element root = builder.parse(
					servletContext.getResourceAsStream("/WEB-INF/security-pages.xml")
			).getDocumentElement();
			
			// get default match result from security pages
			Element defaultSecurity = XmlUtil.getChildElement(root, "default-security-constraint");
			if (defaultSecurity != null) {
				
				Element redirect = XmlUtil.getChildElement(defaultSecurity, "redirect-to");
				if (redirect != null) {
					this.redirectPage = redirect.getTextContent();
				} else {
					Element status = XmlUtil.getChildElement(defaultSecurity, "status-code");
					if (status != null) {
						this.statusCode = Integer.parseInt(status.getTextContent());
					}
				}
			}
			
			// find all resource patterns
			NodeList nodes = root.getChildNodes();
			for (int i = 0, count = nodes.getLength(); i < count; i++) {
				
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					if (node.getNodeName().equals("constrained-resource")) {
						patterns.add(new ConstrainedResources((Element) node));
					} else if (node.getNodeName().equals("unconstrained-resource")) {
						patterns.add(new UnconstrainedResources((Element) node));
					}
				}
			}
		} catch (Exception e) {
			this.logger.error("Fail to load securities /WEB-INF/security-pages.xml from context", e);
		}
		return patterns;
	}
	
	/**
	 * @param path the request URL
	 * @return the matched result
	 */
	private MatchResult getMatchResult(final String path) {
		
		if (this.redirectPage != null) {
			MatchResult result = new MatchResult(path);
			result.set(ResourcePatterns.REDIRECT_PAGE, this.redirectPage);
			return result;
		} else if (this.statusCode != -1) {
			MatchResult result = new MatchResult(path);
			result.set(ResourcePatterns.STATUS_CODE, this.statusCode);
			return result;
		} else {
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Matcher#match(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public MatchResult match(final String path, final HttpServletRequest request) {
		
		if (request.getRemoteUser() == null) {

			if (this.resourcePatterns == null) {
				this.resourcePatterns = this.getResourcePatterns(request.getSession().getServletContext());
			}
			
			for (ResourcePatterns patterns : this.resourcePatterns) {
				if (patterns.match(path)) {
					return patterns.getMatchResult(path);
				}
			}
			return this.getMatchResult(path);
		} else {
			return null;
		}
	}
}
