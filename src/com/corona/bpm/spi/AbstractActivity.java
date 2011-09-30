/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.spi;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import com.corona.bpm.Activity;
import com.corona.bpm.Context;
import com.corona.bpm.ProcessException;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
abstract class AbstractActivity implements Activity {

	/**
	 * the name
	 */
	private String name;
	
	/**
	 * the remark
	 */
	private String remark;
	
	/**
	 * the children nodes
	 */
	private Map<String, Activity> children = new HashMap<String, Activity>();
	
	/**
	 * the parent nodes
	 */
	private Map<String, Activity> parents = new HashMap<String, Activity>();
	
	/**
	 * @param descriptor the descriptor
	 */
	public AbstractActivity(final Element descriptor) {
		this.name = descriptor.getAttribute("name");
		this.remark = descriptor.getAttribute("remark");
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.Activity#getName()
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.Activity#getRemark()
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @return the parent nodes
	 */
	protected Map<String, Activity> getParents() {
		return this.parents;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.Activity#addParent(com.corona.bpm.Activity)
	 */
	public void addParent(final Activity parent) {
		this.parents.put(parent.getName(), parent);
	}
	
	/**
	 * @return the child nodes
	 */
	protected Map<String, Activity> getChildren() {
		return this.children;
	}

	/**
	 * @param child the child node
	 */
	protected void addChild(final Activity child) {
		this.children.put(child.getName(), child);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.Activity#enter(com.corona.bpm.Context)
	 */
	@Override
	public void enter(final Context context) throws ProcessException {
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.Activity#exit(com.corona.bpm.Context)
	 */
	@Override
	public void exit(final Context context) throws ProcessException {
	}
}
