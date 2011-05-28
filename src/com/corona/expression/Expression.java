/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.expression;

import java.io.Serializable;
import java.util.Map;

import org.mvel2.MVEL;

/**
 * <p>The compiled expression that is used to evaluate by context. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Expression {

	/**
	 * the expression
	 */
	private String strExpression;
	
	/**
	 * the compiled get expression
	 */
	private Serializable getExpression = null;
	
	/**
	 * the compiled set expression
	 */
	private Serializable setExpression = null;
	
	/**
	 * @param expression the compiled expression
	 */
	Expression(final String expression) {
		this.strExpression = expression;
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.strExpression;
	}

	/**
	 * @return the get expression
	 */
	private Serializable getGetExpression() {
		
		if (this.getExpression == null) {
			this.getExpression = MVEL.compileExpression(this.strExpression);
		}
		return this.getExpression;
	}

	/**
	 * @return the get expression
	 */
	private Serializable getSetExpression() {
		
		if (this.setExpression == null) {
			this.setExpression = MVEL.compileGetExpression(this.strExpression);
		}
		return this.setExpression;
	}

	/**
	 * @param expression the expression
	 * @return the compiled expression
	 */
	public static Expression compile(final String expression) {
		return new Expression(expression);
	}
	
	/**
	 * @param root the root object
	 * @return the value
	 */
	public Object get(final Object root) {
		return MVEL.executeExpression(this.getGetExpression(), root);
	}

	/**
	 * @param <T> the return type
	 * @param root the root object
	 * @param type the class of return type
	 * @return the value
	 */
	public <T> T get(final Object root, final Class<T> type) {
		return MVEL.executeExpression(this.getGetExpression(), root, type);
	}

	/**
	 * @param context the variable context
	 * @return the value
	 */
	public Object get(final Map<String, ?> context) {
		return MVEL.executeExpression(this.getGetExpression(), context);
	}

	/**
	 * @param <T> the return type
	 * @param context the variable context
	 * @param type the class of return type
	 * @return the value
	 */
	public <T> T get(final Map<String, ?> context, final Class<T> type) {
		return MVEL.executeExpression(this.getGetExpression(), context, type);
	}

	/**
	 * @param root the root object
	 * @param context the variable context
	 * @return the value
	 */
	public Object get(final Object root, final Map<String, ?> context) {
		return MVEL.executeExpression(this.getGetExpression(), root, context);
	}
	
	/**
	 * @param <T> the return type
	 * @param root the root object
	 * @param context the variable context
	 * @param type the class of return type
	 * @return the value
	 */
	public <T> T get(final Object root, final Map<String, ?> context, final Class<T> type) {
		return MVEL.executeExpression(this.getGetExpression(), root, context, type);
	}
	
	/**
	 * @param root the root object
	 * @param value the value to set to property
	 */
	public void set(final Object root, final Object value) {
		MVEL.executeSetExpression(this.getSetExpression(), root, value);
	}

	/**
	 * @param expression the expression
	 * @param root the root object
	 * @return the outcome value
	 */
	public static Object eval(final String expression, final Object root) {
		return MVEL.eval(expression, root);
	}

	/**
	 * @param expression the expression
	 * @param context the variable context
	 * @return the outcome value
	 */
	@SuppressWarnings("unchecked")
	public static Object eval(final String expression, final Map<String, ?> context) {
		return MVEL.eval(expression, (Map<String, Object>) context);
	}

	/**
	 * @param expression the expression
	 * @param root the root object
	 * @param context the variable context
	 * @return the outcome value
	 */
	@SuppressWarnings("unchecked")
	public static Object eval(final String expression, final Object root, final Map<String, ?> context) {
		return MVEL.eval(expression, root, (Map<String, Object>) context);
	}
}
