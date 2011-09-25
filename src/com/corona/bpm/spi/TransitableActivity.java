/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.spi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.corona.bpm.Activity;
import com.corona.bpm.ProcessException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.util.XmlUtil;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TransitableActivity extends AbstractActivity {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(TransitableActivity.class);
	
	/**
	 * all transitions under start activity
	 */
	private List<Transition> transitions = new ArrayList<Transition>();

	/**
	 * @param descriptor the XML descriptor of activity
	 */
	public TransitableActivity(final Element descriptor) {
		
		super(descriptor);
		for (Element element : XmlUtil.getChildElements(descriptor, "transition")) {
			this.transitions.add(new Transition(element));
		}
	}

	/**
	 * @return the transitions
	 */
	protected List<Transition> getTransitions() {
		return this.transitions;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.Activity#setChildren(java.util.Map)
	 */
	@Override
	public void setChildren(final Map<String, Activity> activities) throws ProcessException {
		
		for (Transition transition : this.transitions) {
			
			Activity child = activities.get(transition.getTo());
			if (child == null) {
				this.logger.error(
						"The transition to [{0}] in activity [{1}] doesn't exist", transition.getTo(), this.getName()
				);
				throw new ProcessException(
						"The transition to [{0}] in activity [{1}] doesn't exist", transition.getTo(), this.getName()
				);
			}
			
			transition.setToActivity(child);
			this.addChild(child);
			child.addParent(this);
		}
	}
}
