/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.spi;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.corona.bpm.Activity;
import com.corona.bpm.ProcessException;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
class Definition {

	/**
	 * the id
	 */
	private String id;
	
	/**
	 * the name
	 */
	private String name;
	
	/**
	 * the version
	 */
	private String version;
	
	/**
	 * the start node
	 */
	private Activity start;
	
	/**
	 * all nodes
	 */
	private Map<String, Activity> activities = new HashMap<String, Activity>();
	
	/**
	 * @param resource the resource name
	 * @throws ProcessException if fail to construct process
	 */
	public Definition(final String resource) throws ProcessException {
		this(Definition.class.getResourceAsStream(resource));
	}
	
	/**
	 * @param stream the input stream
	 * @throws ProcessException if fail to construct process
	 */
	public Definition(final InputStream stream) throws ProcessException {
		this(getDocument(stream));
	}
	
	/**
	 * @param document the XML document
	 * @throws ProcessException if fail to construct process
	 */
	public Definition(final Document document) throws ProcessException {
		
		Element root = document.getDocumentElement();
		
		this.id = root.getAttribute("id");
		this.name = root.getAttribute("name");
		this.version = root.getAttribute("version");
		
		NodeList children = root.getChildNodes();
		for (int i = 0, count = children.getLength(); i < count; i++) {
			
			Node node = children.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				
				Activity current = null;
				if ("state".equals(node.getNodeName())) {
					current = new State((Element) node);
				} else if ("decision".equals(node.getNodeName())) {
					current = new Decision((Element) node);
				} else if ("task".equals(node.getNodeName())) {
					current = new Task((Element) node);
				} else if ("start".equals(node.getNodeName())) {
					current = new Start((Element) node);
					this.start = current;
				} else if ("end".equals(node.getNodeName())) {
					current = new End((Element) node);
				}
				
				if (current != null) {
					this.activities.put(current.getName(), current);
				}
			}
		}
		
		for (Activity current : this.activities.values()) {
			
		}
	}
	
	/**
	 * @param stream the input stream
	 * @return the XML document
	 * @throws ProcessException if fail to parse XML document
	 */
	private static Document getDocument(final InputStream stream) throws ProcessException {
		
		DocumentBuilder builder = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (Exception e) {
			throw new ProcessException("Fail to create XML document builder", e);
		}

		try {
			return builder.parse(stream);
		} catch (Exception e) {
			throw new ProcessException("Fail to parse process definition XML by stream", e);
		}
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * @return the start activity
	 */
	public Activity getStart() {
		return start;
	}

	/**
	 * @param activity the activity name
	 * @return the activity or <code>null</code> if does not exist
	 */
	public Activity getActivity(final String activity) {
		return this.activities.get(activity);
	}
}
