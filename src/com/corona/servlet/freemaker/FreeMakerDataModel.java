/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import com.corona.context.ContextManager;
import com.corona.util.CookieUtil;

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
	 * the context manager
	 */
	private ContextManager contextManager;
	
	/**
	 * the FreeMaker engine
	 */
	private FreeMakerEngineManager freeMakerEngineManager;
	
	/**
	 * the child template
	 */
	private String childTemplate;
	
	/**
	 * the current HTTP request
	 */
	private ServletRequest request;
	
	/**
	 * the current HTTP session
	 */
	private HttpSession session;
	
	/**
	 * the current HTTP response
	 */
	private ServletResponse response;
	
	/**
	 * @param contextManager the current context manager
	 * @param root the root outcome from FreeMaker producer
	 */
	public FreeMakerDataModel(final ContextManager contextManager, final Object root) {
		super(root, new BeansWrapper());
		this.contextManager = contextManager;
	}
	
	/**
	 * @param freeMakerEngineManager the freeMakerEngineManager to set
	 */
	public void setFreeMakerEngineManager(final FreeMakerEngineManager freeMakerEngineManager) {
		this.freeMakerEngineManager = freeMakerEngineManager;
	}

	/**
	 * @param childTemplate the child template to set
	 */
	public void setChildTemplate(final String childTemplate) {
		this.childTemplate = childTemplate;
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
		} else if ("request".equals(name)) {
			return this.wrap(this.getRequest());
		} else if ("response".equals(name)) {
			return this.wrap(this.getResponse());
		} else if ("session".equals(name)) {
			return this.wrap(this.getSession());
		} else if (this.freeMakerEngineManager.getThemeTemplateVariableName().equals(name)) {
			return this.wrap(this.childTemplate);
		} else {
			Object component = this.contextManager.get(name);
			return (component != null) ? this.wrap(component) : null;
		}
	}
	
	/**
	 * @return the current HTTP request
	 */
	ServletRequest getRequest() {
		
		if (this.request == null) {
			this.request = this.contextManager.get(ServletRequest.class);
		}
		return this.request;
	}
	
	/**
	 * @return the current HTTP session 
	 */
	HttpSession getSession() {
		
		if (this.session == null) {
			this.session = this.contextManager.get(HttpSession.class);
		}
		return this.session;
	}
	
	/**
	 * @return the current HTTP response
	 */
	ServletResponse getResponse() {
		
		if (this.response == null) {
			this.response = this.contextManager.get(ServletResponse.class);
		}
		return response;
	}
	
	/**
	 * @return get theme name from request or session with this attribute name
	 */
	private String getThemeAttributeName() {
		return this.freeMakerEngineManager.getThemeRequestAttributeName();
	}
	
	/**
	 * @param themeName the theme name
	 * @return the theme template
	 */
	private String getThemeTemplate(final String themeName) {
		return this.freeMakerEngineManager.getThemeTemplates().get(themeName);
	}
	
	/**
	 * @return the theme template
	 */
	String getThemeName() {
		
		// find theme name from cookie first. If can't find, try other way
		String themeName = CookieUtil.getValue(this.getRequest(), this.getThemeAttributeName());
		if (themeName == null) {
			// if theme name is defined as request parameter, will store it to cookie   
			themeName = this.getRequest().getParameter(this.getThemeAttributeName());
			if (themeName != null) {
				CookieUtil.setValue(this.getResponse(), this.getThemeAttributeName(), themeName);
			}
		}
		
		// find theme template by theme name from FreeMaker engine
		String themeTemplate = this.getThemeTemplate(themeName);
		if (themeTemplate == null) {
			// theme name is wrong or does not exist, will uses default theme name
			themeTemplate = this.getThemeTemplate(this.freeMakerEngineManager.getDefaultThemeName());
			
			// store default theme name to cookie for later usage
			CookieUtil.setValue(this.getResponse(), 
					this.getThemeAttributeName(), this.freeMakerEngineManager.getDefaultThemeName()
			);
		}
		
		return themeTemplate;
	}
}
