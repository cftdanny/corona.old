/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.corona.servlet.MatchResult;
import com.corona.util.XmlUtil;

/**
 * <p>All resources that will not constrain user to access </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ResourcePatterns {

	/**
	 * the status code
	 */
	public static final String STATUS_CODE = "status-code";
	
	/**
	 * the redirect to page
	 */
	public static final String REDIRECT_PAGE = "redirect-page";
	
	/**
	 * all resources that will not constrain
	 */
	private List<ResourcePattern> resources = new ArrayList<ResourcePattern>();
	
	/**
	 * @param descriptor the resource descriptor
	 */
	ResourcePatterns(final Element descriptor) {
		
		Element patterns = XmlUtil.getChildElement(descriptor, "page-patterns");
		if (patterns != null) {
			
			NodeList children = patterns.getChildNodes();
			for (int i = 0, count = children.getLength(); i < count; i++) {
				
				Node child = children.item(i);
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					
					if (child.getNodeName().equals("head-page-pattern")) {
						this.resources.add(new HeadResourcePattern((Element) child));
					} else if (child.getNodeName().equals("regex-page-pattern")) {
						this.resources.add(new RegexResourcePattern((Element) child));
					}
				}
			}
		}
	}

	/**
	 * @param path the request URI
	 * @return whether matched with any of its patterns
	 */
	boolean match(final String path) {
		
		for (ResourcePattern pattern : this.resources) {
			if (pattern.match(path)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param path the request URI
	 * @return the match result about this resource patterns
	 */
	public MatchResult getMatchResult(final String path) {
		return null;
	}
}
