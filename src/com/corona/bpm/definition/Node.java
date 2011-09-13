/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.definition;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Node {

	/**
	 * the name
	 */
	private String name;
	
	/**
	 * the children nodes
	 */
	private Map<String, Node> children = new HashMap<String, Node>();
	
	/**
	 * the parent nodes
	 */
	private Map<String, Node> parents = new HashMap<String, Node>();
	
	/**
	 * @param name the name
	 */
	public Node(final String name) {
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return the parent nodes
	 */
	public Map<String, Node> getParents() {
		return this.parents;
	}
	
	/**
	 * @param node the node name
	 * @return the parent node or <code>null</code> if does not exist
	 */
	public Node getParent(final String node) {
		return this.parents.get(name);
	}
	
	/**
	 * @param parent the parent node
	 */
	public void addParent(final Node parent) {
		this.parents.put(parent.getName(), parent);
	}
	
	/**
	 * @return the child nodes
	 */
	public Map<String, Node> getChildren() {
		return this.children;
	}
	
	/**
	 * @param node the node name
	 * @return the child node or <code>null</code> if does not exist
	 */
	public Node getChild(final String node) {
		return this.children.get(node);
	}

	/**
	 * @param child the child node
	 */
	public void addChild(final Node child) {
		this.children.put(child.getName(), child);
	}
}
