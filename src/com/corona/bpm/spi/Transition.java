/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.spi;

import org.w3c.dom.Element;

import com.corona.bpm.Activity;
import com.corona.bpm.Context;
import com.corona.expression.Expression;
import com.corona.util.StringUtil;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
class Transition {

	/**
	 * the name
	 */
	private String name;
	
	/**
	 * the to
	 */
	private String to;
	
	/**
	 * the expression
	 */
	private Expression expr = null;
	
	/**
	 * the remark
	 */
	private String remark;
	
	/**
	 * the activity of to
	 */
	private Activity toActivity;
	
	/**
	 * @param descriptor the descriptor
	 */
	Transition(final Element descriptor) {
		
		this.name = descriptor.getAttribute("name");
		this.to = descriptor.getAttribute("to");
		if (!StringUtil.isBlank(descriptor.getAttribute("expr"))) {
			this.expr = Expression.getExpression(descriptor.getAttribute("expr"));
		}
		this.remark = descriptor.getAttribute("remark");
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expr;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	
	/**
	 * @return the to activity
	 */
	public Activity getToActivity() {
		return toActivity;
	}
	
	/**
	 * @param toActivity the to activity to set
	 */
	public void setToActivity(final Activity toActivity) {
		this.toActivity = toActivity;
	}

	/**
	 * @param context the context
	 * @return whether match this transition
	 */
	public boolean match(final Context context) {
		return (this.expr == null) ? true : (Boolean) this.expr.get(context);
	}
}
