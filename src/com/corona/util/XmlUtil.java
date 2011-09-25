/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.util;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <p>The helper utility for XML </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class XmlUtil {

	/**
	 * utility class
	 */
	private XmlUtil() {
		// do nothing
	}
	
	/**
	 * @param parent the parent XML element
	 * @param childName the child node name
	 * @return the child XML element with specified node name or <code>null</code> if does not exist
	 */
	public static Element getChildElement(final Element parent, final String childName) {
	
		Element child = null;
		if (parent != null) {
			NodeList children = parent.getElementsByTagName(childName);
			if (children.getLength() > 0) {
				child = (Element) children.item(0);
			}
		}
		return child;
	}
	
	/**
	 * @param parent the parent XML element
	 * @param childName the child node name
	 * @return the list of child XML elements with specified node name
	 */
	public static List<Element> getChildElements(final Element parent, final String childName) {
		
		if (parent != null) {
			
			List<Element> result = new ArrayList<Element>();
			NodeList nodes = parent.getElementsByTagName(childName);
			for (int i = 0, count = nodes.getLength(); i < count; i++) {
				result.add((Element) nodes.item(i));
			}
			return result;
		} else {
			return null;
		}
	}
	
	/**
	 * @param parent the parent XML element
	 * @param childName the child node name
	 * @return the child XML element text with specified node name or <code>null</code> if does not exist
	 */
	public static String getChildElementText(final Element parent, final String childName) {
		
		Element child = getChildElement(parent, childName);
		return child == null ? null : child.getTextContent();
	}

	/**
	 * @param parent the parent XML element
	 * @param childName the child node name
	 * @param defaultText the default text if child element does not exist
	 * @return the child XML element text with specified node name or default value if does not exist
	 */
	public static String getChildElementText(final Element parent, final String childName, final String defaultText) {
		
		Element child = getChildElement(parent, childName);
		return child == null ? defaultText : child.getTextContent();
	}
}
