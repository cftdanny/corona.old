/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.definition;

import org.w3c.dom.Element;

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
	 * the to
	 */
	private String to;
	
	/**
	 * the expression
	 */
	private Expression expr = null;
	
	/**
	 * @param descriptor the descriptor
	 */
	Transition(final Element descriptor) {
		
		this.to = descriptor.getAttribute("to");
		if (!StringUtil.isBlank(descriptor.getAttribute("expr"))) {
			this.expr = Expression.getExpression(descriptor.getAttribute("expr"));
		}
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
}
