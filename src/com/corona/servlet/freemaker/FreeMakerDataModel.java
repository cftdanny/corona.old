/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import com.corona.context.ContextManager;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * <p>This FreeMaker template model is used to directly export properties, method to template. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class FreeMakerDataModel extends BeanModel {

	/**
	 * the variable name for ServletRequest
	 */
	private static final String REQUEST = "request";
	
	/**
	 * the variable name for ServletResponse
	 */
	private static final String RESPONSE = "response";
	
	/**
	 * the variable name for HttpSession
	 */
	private static final String SESSION = "session";
	
	/**
	 * the context manager
	 */
	private ContextManager contextManager;
	
	/**
	 * @param contextManager the current context manager
	 * @param root the root outcome from FreeMaker producer
	 */
	public FreeMakerDataModel(final ContextManager contextManager, final Object root) {
		super(root, new BeansWrapper());
		this.contextManager = contextManager;
	}

	/**
	 * {@inheritDoc}
	 * @see freemarker.ext.beans.BeanModel#get(java.lang.String)
	 */
	@Override
	public TemplateModel get(final String name) throws TemplateModelException {
		
		TemplateModel model = super.get(name);
		if (model != null) {
			return model;
		} else if (REQUEST.equals(name)) {
			return this.wrap(this.contextManager.get(ServletRequest.class));
		} else if (RESPONSE.equals(name)) {
			return this.wrap(this.contextManager.get(ServletResponse.class));
		} else if (SESSION.equals(name)) {
			return this.wrap(this.contextManager.get(HttpSession.class));
		} else {
			return null;
		}
	}
}
